package com.example.bridgenotes.persistence.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "deals",
    foreignKeys = [
        ForeignKey(
            entity = TournamentEntity::class,
            parentColumns = ["id"],
            childColumns = ["tournamentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("tournamentId")]
)
data class DealEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val tournamentId: Long = 0L,
    val dealNumber: String,
    val opponents: String,
    val contract: String,
    val declarer: String,
    val result: String,
    val score: String,
    val notes: String
)
