package com.example.bridgenotes

import Deal
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bridgenotes.persistence.database.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TournamentViewModel(
    private val repository: DataRepository
) : ViewModel() {

    val tournaments: StateFlow<List<Tournament>> = repository.getAllTournaments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _currentTournament = MutableStateFlow<Tournament?>(null)
    val currentTournament: StateFlow<Tournament?> = _currentTournament.asStateFlow()

    private val _currentDeal = MutableStateFlow<Deal?>(null)
    val currentDeal: StateFlow<Deal?> = _currentDeal.asStateFlow()

    init {
//        viewModelScope.launch {
//            for (tournament in createDemoData()) {
//                repository.createTournament(tournament)
//            }
//        }
    }

    private fun createDemoData(): List<Tournament> {
        return listOf(
            Tournament(
                id = 0,
                name = "Velká cena Prahy - 1. kolo",
                date = java.time.LocalDateTime.of(2024, 12, 11, 12, 0),
                resultsLink = "http://results.bridgepraha.cz/vcp2024_1",
                pairOrTeam = "Tomis - Kaštovský",
                note = "První kolo série Velká cena Prahy",
                deals = listOf(
                    Deal(
                        dealNumber = "1",
                        opponents = "Svoboda - Kopecký",
                        contract = "3NT",
                        declarer = "E",
                        result = "+1",
                        score = "-430",
                        notes = "Zdeněk to pokazil, musí vrátit ve třetím štychu piku."
                    ),
                    Deal(
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
                name = "VC Neratovic",
                date = java.time.LocalDateTime.of(2024, 3, 20, 17, 0),
                resultsLink = "http://results.bridgepraha.cz/vcn2024",
                pairOrTeam = "Tomis - Kaštovský",
                note = "Večerní turnaj v Neratovicích",
                deals = listOf(
                    Deal(
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
    }

    fun loadTournamentAndDeal(tournamentId: Long, dealId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val tournament = repository.getTournament(tournamentId)
            _currentTournament.value = tournament
            _currentDeal.value = tournament?.deals?.find { it.id == dealId }
            _isLoading.value = false
        }
    }

    fun createTournament(tournament: Tournament) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.createTournament(tournament)
            _isLoading.value = false
        }
    }

    fun updateTournament(tournament: Tournament) {
        viewModelScope.launch {
            repository.updateTournament(tournament)
        }
    }

    fun createDeal(tournamentId: Long, deal: Deal) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.createDeal(deal)
            _isLoading.value = false
        }
    }

    fun updateDeal(tournamentId: Long, deal: Deal) {
        viewModelScope.launch {
            repository.updateDeal(deal)
        }
    }

    fun deleteTournament(tournament: Tournament) {
        viewModelScope.launch {
            repository.deleteTournament(tournament)
        }
    }

    fun deleteDeal(deal: Deal) {
        viewModelScope.launch {
            repository.deleteDeal(deal)
        }
    }
}
