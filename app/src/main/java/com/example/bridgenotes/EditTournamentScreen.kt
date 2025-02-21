package com.example.bridgenotes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EditTournamentScreen(tournamentId: String, onNavigateBack: () -> Unit) {
    Column {
        Text("Edit Tournament Screen")
        Text("Tournament ID: $tournamentId")
        Button(onClick = onNavigateBack) {
            Text("Back to Tournament Detail")
        }
        // TODO: Add form fields for editing the tournament
    }
} 