package com.example.bridgenotes

import Deal
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDealScreen(
    dealId: String,
    tournamentId: String,
    onNavigateBack: () -> Unit,
    viewModel: TournamentViewModel = viewModel()
) {
    val deal by viewModel.tournaments.collectAsState()
        .value.find { it.id == tournamentId }
        ?.deals?.find { it.id == dealId }
        .let { remember(it) { mutableStateOf(it) } }
    
    // State for form fields - initialize with empty strings
    var opponents by remember { mutableStateOf("") }
    var contract by remember { mutableStateOf("") }
    var declarer by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    // Update state when deal changes
    LaunchedEffect(deal) {
        deal?.let { currentDeal ->
            opponents = currentDeal.opponents
            contract = currentDeal.contract
            declarer = currentDeal.declarer
            result = currentDeal.result
            score = currentDeal.score
            notes = currentDeal.notes
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_deal)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        // Save deal
                        deal?.let { currentDeal ->
                            viewModel.updateDeal(
                                tournamentId,
                                currentDeal.copy(
                                    opponents = opponents,
                                    contract = contract,
                                    declarer = declarer,
                                    result = result,
                                    score = score,
                                    notes = notes,
                                    dealNumber = currentDeal.dealNumber
                                )
                            )
                        } ?: run {
                            val newDeal = Deal(
                                id = dealId,
                                tournamentId = tournamentId,
                                dealNumber = dealId,
                                opponents = opponents,
                                contract = contract,
                                declarer = declarer,
                                result = result,
                                score = score,
                                notes = notes
                            )
                            viewModel.createDeal(tournamentId, newDeal)
                            // Force refresh after creation
                        }
                        onNavigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.save_changes)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val currentDeal = deal) {
            null -> {
                Box(Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(R.string.loading),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            else -> {
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
    }
} 