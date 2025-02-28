package com.bridge.notes.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bridge.notes.persistence.entity.DealEntity
import com.bridge.notes.persistence.entity.TournamentEntity
import com.bridge.notes.persistence.entity.TournamentWithDeals
import kotlinx.coroutines.flow.Flow

@Dao
interface TournamentDao {
    // Use this method to insert new tournaments.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTournament(tournament: TournamentEntity): Long

    @Update
    suspend fun updateTournament(tournament: TournamentEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDeal(deal: DealEntity): Long

    @Update
    suspend fun updateDeal(deal: DealEntity)

    @Delete
    suspend fun deleteDeal(deal: DealEntity)

    @Transaction
    @Query("SELECT * FROM tournaments WHERE id = :tournamentId")
    suspend fun getTournamentWithDeals(tournamentId: Long): TournamentWithDeals?

    @Transaction
    @Query("SELECT * FROM tournaments")
    fun getAllTournamentsWithDeals(): Flow<List<TournamentWithDeals>>

    @Delete
    suspend fun deleteTournament(tournament: TournamentEntity)
}
