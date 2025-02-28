package com.bridge.notes.mapper

import com.bridge.notes.model.Deal
import com.bridge.notes.model.Tournament
import com.bridge.notes.persistence.entity.DealEntity
import com.bridge.notes.persistence.entity.TournamentWithDeals
import com.bridge.notes.persistence.entity.TournamentEntity


object Mappers {

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
