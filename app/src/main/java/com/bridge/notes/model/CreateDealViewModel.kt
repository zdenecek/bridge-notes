package com.bridge.notes.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.notes.persistence.database.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateDealViewModel(
    private val repository: DataRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    fun createDeal(deal: Deal) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.createDeal(deal)
            _isLoading.value = false
        }
    }

    suspend fun getTournamentName(tournamentId: Long): String? {
        _isLoading.value = true
        return try {
            repository.getTournament(tournamentId)?.name
        } catch (e: Exception) {
            null
        } finally {
            _isLoading.value = false
        }
    }
}