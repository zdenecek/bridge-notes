package com.example.bridgenotes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDealScreen(dealId: String, onNavigateBack: () -> Unit) {
    var opponents by remember { mutableStateOf("") }
    var contract by remember { mutableStateOf("") }
    var declarer by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upravit rozdání") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        // TODO: Implement save
                        onNavigateBack()
                    }) {
                        Text("✓")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = opponents,
                onValueChange = { opponents = it },
                label = { Text("Soupeři") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = contract,
                onValueChange = { contract = it },
                label = { Text("Závazek") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = declarer,
                onValueChange = { declarer = it },
                label = { Text("Sehrávající") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = result,
                onValueChange = { result = it },
                label = { Text("Výsledek") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = score,
                onValueChange = { score = it },
                label = { Text("Skóre") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Poznámka") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        }
    }
} 