package com.example.bridgenotes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DealDetailScreen(
    dealId: String,
    onNavigateBack: () -> Unit,
    onNavigateToEditDeal: () -> Unit,
    onNavigateToCreateDeal: () -> Unit
) {
    Column {
        Text("Deal Detail Screen")
        Text("Deal ID: $dealId")
        Button(onClick = onNavigateBack) {
            Text("Back to Tournament Detail")
        }
        Button(onClick = onNavigateToEditDeal) {
            Text("Edit Deal")
        }
        Button(onClick = onNavigateToCreateDeal) {
            Text("Create New Deal")
        }
        // TODO: Display deal details
    }
} 