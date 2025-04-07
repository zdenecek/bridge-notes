package com.bridge.notes.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bridge.notes.persistence.database.DatabaseProvider
import com.bridge.notes.persistence.database.DataRepository

class TournamentViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = DatabaseProvider.getDatabase(context)
        val repository = DataRepository(database.tournamentDao())
        
        return when {
            modelClass.isAssignableFrom(TournamentListViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                TournamentListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(TournamentDetailsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                TournamentDetailsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DealViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                DealViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}