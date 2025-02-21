package com.example.bridgenotes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CreateTournamentScreen(onNavigateBack: () -> Unit) {
    Column {
        Text("Create Tournament Screen")
        Button(onClick = onNavigateBack) {
            Text("Back to Tournament List")
        }
        // TODO: Add form fields for creating a new tournament
    }
} 