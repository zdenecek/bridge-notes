package com.example.bridgenotes.persistence.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TournamentWithDeals(
    @Embedded val tournament: TournamentEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "tournamentId"
    )
    val deals: List<DealEntity>
)
