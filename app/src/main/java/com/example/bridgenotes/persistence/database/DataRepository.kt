package com.example.bridgenotes.persistence.database

import Deal
import com.example.bridgenotes.Tournament
import com.example.bridgenotes.Mappers.toDomainModel
import com.example.bridgenotes.Mappers.toEntity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TournamentRepository(private val tournamentDao: TournamentDao) {

    fun getAllTournaments(): Flow<List<Tournament>> {
        return tournamentDao.getAllTournamentsWithDeals().map { list ->
            list.map { it.toDomainModel() }
        }
    }

    suspend fun getTournament(tournamentId: String): Tournament? {
        // Assuming your database IDs are stored as Long, convert if necessary.
        val id = tournamentId
        val tournamentWithDeals = tournamentDao.getTournamentWithDeals(id)
        return tournamentWithDeals?.toDomainModel()
    }

    suspend fun createTournament(tournament: Tournament) {
        // Insert tournament and retrieve the new id.
        val newId = tournamentDao.insertTournament(tournament.toEntity())
        // If there are deals, insert them with the correct tournament id.
        tournament.deals.forEach { deal ->
            // Ensure the deal uses the new tournament id.
            tournamentDao.insertDeal(deal.toEntity().copy(tournamentId = newId.toString()))
        }
    }

    suspend fun updateTournament(tournament: Tournament) {
        tournamentDao.insertTournament(tournament.toEntity())
    }

    suspend fun createDeal(deal: Deal) {
        tournamentDao.insertDeal(deal.toEntity())
    }

    suspend fun updateDeal(deal: Deal) {
        tournamentDao.insertDeal(deal.toEntity())
    }
}
