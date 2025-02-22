package com.example.bridgenotes

import Deal
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow

class TournamentViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _tournaments = MutableStateFlow(listOf<Tournament>())
    val tournaments = _tournaments.asStateFlow()

    // In a real app, this would be injected as a repository
    private val sampleTournaments = listOf(
        Tournament(
            id = "1",
            name = "Velká cena Prahy - 1. kolo",
            date = java.time.LocalDateTime.of(2024, 3, 15, 9, 30),
            resultsLink = "http://results.bridgepraha.cz/vcp2024_1",
            pairOrTeam = "Tomis - Kaštovský",
            note = "První kolo série Velká cena Prahy",
            deals = listOf(
                Deal(
                    id = "1",
                    tournamentId = "1",
                    opponents = "Svoboda - Kopecký",
                    contract = "3NT",
                    declarer = "E",
                    result = "+1",
                    score = "-430",
                    notes = "Zdeněk to pokazil, musí vrátit ve třetím štychu piku."
                ),
                Deal(
                    id = "2",
                    tournamentId = "1",
                    opponents = "Medlín - Martynek",
                    contract = "4♠",
                    declarer = "S",
                    result = "=",
                    score = "+420",
                    notes = "Správná obrana by porazila závazek."
                )
            )
        ),
        Tournament(
            id = "2",
            name = "VC Neratovic",
            date = java.time.LocalDateTime.of(2024, 3, 20, 17, 0),
            resultsLink = "http://results.bridgepraha.cz/vcn2024",
            pairOrTeam = "Tomis - Kaštovský",
            note = "Večerní turnaj v Neratovicích",
            deals = listOf(
                Deal(
                    id = "1",
                    tournamentId = "2",
                    opponents = "Nulíček - Vozábal",
                    contract = "6♥",
                    declarer = "W",
                    result = "-1",
                    score = "+100",
                    notes = "Dobrá obrana porazila slem."
                )
            )
        )
    )

    init {
        loadData() // Load initial data when ViewModel is created
    }

    private fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            // Load sample data if tournaments is empty
            if (_tournaments.value.isEmpty()) {
                _tournaments.value = sampleTournaments
            }
            _isLoading.value = false
        }
    }

    fun getTournamentById(id: String): Flow<Tournament?> = flow {
        val tournament = _tournaments.value.find { it.id == id }
        emit(tournament)
    }

    fun getDealById(tournamentId: String, dealId: String): Flow<Deal?> = flow {
        val tournament = _tournaments.value.find { it.id == tournamentId }
        val deal = tournament?.deals?.find { it.id == dealId }
        emit(deal)
    }

    fun updateDeal(tournamentId: String, deal: Deal) {
        viewModelScope.launch {
            _tournaments.update { tournaments ->
                tournaments.map { tournament ->
                    if (tournament.id == tournamentId) {
                        tournament.copy(
                            deals = tournament.deals.map { 
                                if (it.id == deal.id) deal else it 
                            }
                        )
                    } else tournament
                }
            }
        }
    }

    fun createDeal(tournamentId: String, deal: Deal) {
        viewModelScope.launch {
            _tournaments.update { tournaments ->
                tournaments.map { tournament ->
                    if (tournament.id == tournamentId) {
                        tournament.copy(deals = tournament.deals + deal)
                    } else tournament
                }
            }
        }
    }

    fun updateTournament(tournament: Tournament) {
        viewModelScope.launch {
            _tournaments.update { tournaments ->
                tournaments.map { 
                    if (it.id == tournament.id) tournament else it 
                }
            }
        }
    }

    fun createTournament(tournament: Tournament) {
        viewModelScope.launch {
            _isLoading.value = true
            _tournaments.update { currentTournaments ->
                currentTournaments + tournament
            }
            _isLoading.value = false
        }
    }
}