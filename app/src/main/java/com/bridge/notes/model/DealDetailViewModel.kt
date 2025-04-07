package com.bridge.notes.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.notes.persistence.database.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DealDetailViewModel(
    private val repository: DataRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _currentDeal = MutableStateFlow<Deal?>(null)
    val currentDeal: StateFlow<Deal?> = _currentDeal.asStateFlow()

    fun loadDeal(tournamentId: Long, dealId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val tournament = repository.getTournament(tournamentId)
            _currentDeal.value = tournament?.deals?.find { it.id == dealId }
            _isLoading.value = false
        }
    }

    fun deleteDeal(deal: Deal) {
        viewModelScope.launch {
            repository.deleteDeal(deal)
        }
    }
}