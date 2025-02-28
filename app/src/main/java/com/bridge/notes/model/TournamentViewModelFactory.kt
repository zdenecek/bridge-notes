package com.bridge.notes.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bridge.notes.persistence.database.DatabaseProvider
import com.bridge.notes.persistence.database.DataRepository

class TournamentViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TournamentViewModel::class.java)) {
            val database = DatabaseProvider.getDatabase(context)
            val repository = DataRepository(database.tournamentDao())
            @Suppress("UNCHECKED_CAST")
            return TournamentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}