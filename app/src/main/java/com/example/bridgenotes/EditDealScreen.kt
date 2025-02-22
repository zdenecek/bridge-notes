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
    val deal by viewModel.getDealById(tournamentId, dealId).collectAsState(initial = null)
    
    // State for form fields
    var opponents by remember(deal) { mutableStateOf(deal?.opponents ?: "") }
    var contract by remember(deal) { mutableStateOf(deal?.contract ?: "") }
    var declarer by remember(deal) { mutableStateOf(deal?.declarer ?: "") }
    var result by remember(deal) { mutableStateOf(deal?.result ?: "") }
    var score by remember(deal) { mutableStateOf(deal?.score ?: "") }
    var notes by remember(deal) { mutableStateOf(deal?.notes ?: "") }

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
                        deal?.let {
                            viewModel.updateDeal(
                                tournamentId,
                                it.copy(
                                    opponents = opponents,
                                    contract = contract,
                                    declarer = declarer,
                                    result = result,
                                    score = score,
                                    notes = notes
                                )
                            )
                        } ?: viewModel.createDeal(
                            tournamentId,
                            Deal(
                                id = dealId,
                                tournamentId = tournamentId,
                                opponents = opponents,
                                contract = contract,
                                declarer = declarer,
                                result = result,
                                score = score,
                                notes = notes
                            )
                        )
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
        if (deal == null) {
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(R.string.loading),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
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