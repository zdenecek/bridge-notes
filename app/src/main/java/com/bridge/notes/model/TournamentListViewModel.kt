package com.bridge.notes.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.notes.persistence.database.DataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TournamentListViewModel(
    private val repository: DataRepository
) : ViewModel() {

    val tournaments = repository.getAllTournaments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createTournament(tournament: Tournament) {
        viewModelScope.launch {
            repository.createTournament(tournament)
        }
    }

    fun deleteTournament(tournament: Tournament) {
        viewModelScope.launch {
            repository.deleteTournament(tournament)
        }
    }

    fun insertDemoData() {
        viewModelScope.launch {
            for (tournament in createDemoData()) {
                repository.createTournament(tournament)
            }
        }
    }

    private fun createDemoData(): List<Tournament> {
        // Copy the demo data creation logic from TournamentViewModel
        return listOf(
            Tournament(
                name = "Velká cena Prahy - 1. kolo",
                date = java.time.LocalDateTime.of(2024, 12, 11, 12, 0),
                resultsLink = "http://results.bridgepraha.cz/vcp2024_1",
                pairOrTeam = "Tomis - Kaštovský",
                note = "První kolo série Velká cena Prahy"
            ),
            // ... rest of the demo data ...
        )
    }
} 