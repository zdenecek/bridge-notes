package com.example.bridgenotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TournamentViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.onStart { loadData() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    
    private val _tournaments = MutableStateFlow(listOf<Tournament>())
    val tournaments = _tournaments.asStateFlow()

    private fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            _tournaments.update { _ ->
                listOf(Tournament("Praha open 2012"), Tournament("Ostrava open 2013"))
            }
            _isLoading.value = false
        }
    }
}