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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.delay

class TournamentViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

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
                    dealNumber = "1",
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
                    dealNumber = "2",
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
                    dealNumber = "1",
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


    private val _tournaments = MutableStateFlow(listOf<Tournament>())
    val tournaments = _tournaments.asStateFlow()

    private val _currentTournament = MutableStateFlow<Tournament?>(null)
    val currentTournament: StateFlow<Tournament?> = _currentTournament.asStateFlow()

    private val _currentDeal = MutableStateFlow<Deal?>(null)
    val currentDeal: StateFlow<Deal?> = _currentDeal.asStateFlow()

    // In a real app, this would be injected as a repository

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
            _isLoading.value = true
            
            println("VM Before - Tournament $tournamentId deals: ${
                _tournaments.value.find { it.id == tournamentId }?.deals?.map { it.id }
            }")
            
            // Create new immutable lists
            val currentTournaments = _tournaments.value.toList()
            val updatedTournaments = currentTournaments.map { tournament ->
                if (tournament.id == tournamentId) {
                    // Create a new immutable list of deals
                    val newDeals = tournament.deals.toList() + deal
                    tournament.copy(deals = newDeals)
                } else {
                    tournament
                }
            }
            
            // Force a new state update
            _tournaments.update { updatedTournaments }
            
            // Double-check the update happened
            println("VM Immediate verification - Tournament $tournamentId deals: ${
                _tournaments.value.find { it.id == tournamentId }?.deals?.map { it.id }
            }")
            
            // Add a delay and verify again
            delay(100)
            println("VM Delayed verification - Tournament $tournamentId deals: ${
                _tournaments.value.find { it.id == tournamentId }?.deals?.map { it.id }
            }")
            
            _isLoading.value = false
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

    fun refreshDeal(tournamentId: String, dealId: String) {
        viewModelScope.launch {
            val tournament = _tournaments.value.find { it.id == tournamentId }
            val deal = tournament?.deals?.find { it.id == dealId }
            if (deal != null) {
                // Force an update to trigger recomposition
                _tournaments.update { it.toList() }
            }
        }
    }

    fun loadTournamentAndDeal(tournamentId: String, dealId: String) {
        viewModelScope.launch {
            println("Loading tournament $tournamentId and deal $dealId")
            println("Available tournaments: ${_tournaments.value.map { it.id }}")
            println("Current tournament deals: ${
                _tournaments.value.find { it.id == tournamentId }?.deals?.map { it.id }
            }")
            
            val tournament = _tournaments.value.find { it.id == tournamentId }
            _currentTournament.value = tournament
            
            val deal = tournament?.deals?.find { it.id == dealId }
            _currentDeal.value = deal
            
            println("Loaded tournament: ${tournament?.name}, deals: ${tournament?.deals?.map { it.id }}")
        }
    }

    // Add a function to force refresh the tournaments state
    fun refreshTournaments() {
        viewModelScope.launch {
            val currentTournaments = _tournaments.value
            _tournaments.value = currentTournaments.toList()
            println("Refreshed tournaments state: ${_tournaments.value.map { "${it.id}: ${it.deals.map { d -> d.id }}" }}")
        }
    }

    // Add a new function to help debug
    fun getTournamentDeals(tournamentId: String): List<Deal> {
        return _tournaments.value
            .find { it.id == tournamentId }
            ?.deals
            ?: emptyList()
    }
}