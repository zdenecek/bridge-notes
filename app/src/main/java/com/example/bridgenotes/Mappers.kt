package com.example.bridgenotes

import Deal
import com.example.bridgenotes.persistence.database.DealEntity
import com.example.bridgenotes.persistence.database.TournamentWithDeals
import com.example.bridgenotes.persistence.database.TournamentEntity


object Mappers {

    // Convert a database entity (com.example.bridgenotes.persistence.database.TournamentWithDeals) to a domain model.
    fun TournamentWithDeals.toDomainModel(): Tournament {
        return Tournament(
            id = this.tournament.id,
            name = this.tournament.name,
            date = this.tournament.date,
            resultsLink = this.tournament.resultsLink,
            pairOrTeam = this.tournament.pairOrTeam,
            note = this.tournament.note,
            deals = this.deals.map { it.toDomainModel() }
        )
    }

    // Convert a domain model to a database entity (TournamentEntity)
    fun Tournament.toEntity(): TournamentEntity {
        return TournamentEntity(
            id = this.id,
            name = this.name,
            date = this.date,
            resultsLink = this.resultsLink,
            pairOrTeam = this.pairOrTeam,
            note = this.note
        )
    }

    // Convert a database entity (com.example.bridgenotes.persistence.database.DealEntity) to a domain model.
    fun DealEntity.toDomainModel(): Deal {
        return Deal(
            id = this.id,
            tournamentId = this.tournamentId,
            dealNumber = this.dealNumber,
            opponents = this.opponents,
            contract = this.contract,
            declarer = this.declarer,
            result = this.result,
            score = this.score,
            notes = this.notes
        )
    }

    // Convert a domain model (Deal) to a database entity (com.example.bridgenotes.persistence.database.DealEntity).
    fun Deal.toEntity(): DealEntity {
        return DealEntity(
            id = this.id,
            tournamentId = this.tournamentId,
            dealNumber = this.dealNumber,
            opponents = this.opponents,
            contract = this.contract,
            declarer = this.declarer,
            result = this.result,
            score = this.score,
            notes = this.notes
        )
    }
}
