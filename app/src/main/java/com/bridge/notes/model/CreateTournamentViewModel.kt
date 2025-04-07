package com.bridge.notes.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.notes.persistence.database.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateTournamentViewModel(
    private val repository: DataRepository
) : ViewModel() {

    fun createTournament(tournament: Tournament) {
        viewModelScope.launch {
            repository.createTournament(tournament)
        }
    }
}