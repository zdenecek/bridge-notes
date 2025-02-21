package com.example.bridgenotes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EditDealScreen(dealId: String, onNavigateBack: () -> Unit) {
    Column {
        Text("Edit Deal Screen")
        Text("Deal ID: $dealId")
        Button(onClick = onNavigateBack) {
            Text("Back to Deal Detail")
        }
        // TODO: Add form fields for editing the deal
    }
} 