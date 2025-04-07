package com.bridge.notes.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.notes.persistence.database.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditTournamentViewModel(
    private val repository: DataRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _currentTournament = MutableStateFlow<Tournament?>(null)
    val currentTournament: StateFlow<Tournament?> = _currentTournament.asStateFlow()

    fun loadTournament(tournamentId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val tournament = repository.getTournament(tournamentId)
            _currentTournament.value = tournament
            _isLoading.value = false
        }
    }

    fun updateTournament(tournament: Tournament) {
        viewModelScope.launch {
            repository.updateTournament(tournament)
        }
    }
}