package com.bridge.notes.persistence.database

import com.bridge.notes.model.Tournament
import com.bridge.notes.mapper.Mappers.toDomainModel
import com.bridge.notes.mapper.Mappers.toEntity
import com.bridge.notes.model.Deal
import com.bridge.notes.persistence.dao.TournamentDao

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

    suspend fun deleteTournament(tournament: Tournament) {
        tournamentDao.deleteTournament(tournament.toEntity())
    }

    suspend fun deleteDeal(deal: Deal) {
        tournamentDao.deleteDeal(deal.toEntity())
    }
}
