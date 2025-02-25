package com.example.bridgenotes

import Deal
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Check
import java.util.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.compose.runtime.rememberCoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDealScreen(
    tournamentId: Long,
    onNavigateBack: () -> Unit,
    viewModel: TournamentViewModel
) {
    var dealNumber by remember { mutableStateOf("") }
    var opponents by remember { mutableStateOf("") }
    var contract by remember { mutableStateOf("") }
    var declarer by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val tournaments by viewModel.tournaments.collectAsState()
    val tournament = tournaments.find { it.id == tournamentId }
    val scope = rememberCoroutineScope()

    // Add state tracking for the new deal
    var createdDealId by remember { mutableStateOf<Long?>(null) }

    // Add effect to monitor deal creation
    LaunchedEffect(createdDealId) {
        if (createdDealId != null) {
            delay(200) // Give time for state to update
            println("Deal creation completed, navigating back")
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_deal)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val newDeal = Deal(
                                id = 0,
                                tournamentId = tournamentId,
                                dealNumber = dealNumber,
                                opponents = opponents,
                                contract = contract,
                                declarer = declarer,
                                result = result,
                                score = score,
                                notes = notes
                            )
                            
                            scope.launch {
                                println("Creating new deal: ${newDeal.id}")
                                viewModel.createDeal(tournamentId, newDeal)
                                createdDealId = newDeal.id // Set the ID after creation
                            }
                        },
                        enabled = true
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.save_changes)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Tournament name (uneditable)
            OutlinedTextField(
                value = tournament?.name ?: "",
                onValueChange = { },
                label = { Text(stringResource(R.string.deal_tournament_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )
            
            OutlinedTextField(
                value = dealNumber,
                onValueChange = { dealNumber = it },
                label = { Text(stringResource(R.string.deal_number_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = opponents,
                onValueChange = { opponents = it },
                label = { Text(stringResource(R.string.deal_opponents_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = contract,
                onValueChange = { contract = it },
                label = { Text(stringResource(R.string.deal_contract_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = declarer,
                onValueChange = { declarer = it },
                label = { Text(stringResource(R.string.deal_declarer_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = result,
                onValueChange = { result = it },
                label = { Text(stringResource(R.string.deal_result_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = score,
                onValueChange = { score = it },
                label = { Text(stringResource(R.string.deal_score_label)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text(stringResource(R.string.deal_notes_label)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        }
    }
}
