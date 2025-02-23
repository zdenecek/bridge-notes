package com.example.bridgenotes.persistence.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TournamentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTournament(tournament: TournamentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeal(deal: DealEntity): Long

    @Transaction
    @Query("SELECT * FROM tournaments WHERE id = :tournamentId")
    suspend fun getTournamentWithDeals(tournamentId: String): TournamentWithDeals?

    @Transaction
    @Query("SELECT * FROM tournaments")
    fun getAllTournamentsWithDeals(): Flow<List<TournamentWithDeals>>
}
