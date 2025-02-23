package com.example.bridgenotes

import Deal
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bridgenotes.persistence.database.TournamentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TournamentViewModel(
    private val repository: TournamentRepository
) : ViewModel() {

    // Expose tournaments as a StateFlow (using repository flow)
    val tournaments: StateFlow<List<Tournament>> = repository.getAllTournaments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _currentTournament = MutableStateFlow<Tournament?>(null)
    val currentTournament: StateFlow<Tournament?> = _currentTournament.asStateFlow()

    private val _currentDeal = MutableStateFlow<Deal?>(null)
    val currentDeal: StateFlow<Deal?> = _currentDeal.asStateFlow()

    fun loadTournamentAndDeal(tournamentId: String, dealId: String) {
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

    fun createDeal(tournamentId: String, deal: Deal) {
        viewModelScope.launch {
            _isLoading.value = true
            // The deal should already contain the correct tournamentId.
            repository.createDeal(deal)
            _isLoading.value = false
        }
    }

    fun updateDeal(tournamentId: String, deal: Deal) {
        viewModelScope.launch {
            repository.updateDeal(deal)
        }
    }
}
