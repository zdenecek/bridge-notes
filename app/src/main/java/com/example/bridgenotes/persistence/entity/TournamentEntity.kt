package com.example.bridgenotes.persistence.entity



import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tournaments")
data class TournamentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val date: LocalDateTime,
    val resultsLink: String,
    val pairOrTeam: String,
    val note: String,
)