package com.bridge.notes.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bridge.notes.persistence.database.DatabaseProvider
import com.bridge.notes.persistence.database.DataRepository

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
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
            modelClass.isAssignableFrom(EditTournamentViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                EditTournamentViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CreateTournamentViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                CreateTournamentViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CreateDealViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                CreateDealViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EditDealViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                EditDealViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DealDetailViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                DealDetailViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}