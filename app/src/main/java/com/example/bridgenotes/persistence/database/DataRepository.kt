package com.example.bridgenotes.persistence.database

import Deal
import com.example.bridgenotes.Tournament
import com.example.bridgenotes.Mappers.toDomainModel
import com.example.bridgenotes.Mappers.toEntity
import com.example.bridgenotes.persistence.dao.TournamentDao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataRepository(private val tournamentDao: TournamentDao) {

    fun getAllTournaments(): Flow<List<Tournament>> {
        return tournamentDao.getAllTournamentsWithDeals().map { list ->
            list.map { it.toDomainModel() }
        }
    }

    suspend fun getTournament(tournamentId: Long): Tournament? {
        val id = tournamentId
        val tournamentWithDeals = tournamentDao.getTournamentWithDeals(id)
        return tournamentWithDeals?.toDomainModel()
    }

    suspend fun createTournament(tournament: Tournament) {
        val newId = tournamentDao.insertTournament(tournament.toEntity())
        tournament.deals.forEach { deal ->
            tournamentDao.insertDeal(deal.toEntity().copy(tournamentId = newId))
        }
    }

    suspend fun updateTournament(tournament: Tournament) {
        tournamentDao.updateTournament(tournament.toEntity())
    }

    suspend fun createDeal(deal: Deal) {
        tournamentDao.insertDeal(deal.toEntity())
    }

    suspend fun updateDeal(deal: Deal) {
        tournamentDao.updateDeal(deal.toEntity())
    }
}
