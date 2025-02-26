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
    dealId: Long,
    tournamentId: Long,
    onNavigateBack: () -> Unit,
    viewModel: TournamentViewModel = viewModel()
) {
    val deal = viewModel.tournaments.collectAsState().value
        .find { it.id == tournamentId }
        ?.deals?.find { it.id == dealId }

    if (deal == null) {
        LaunchedEffect(Unit) {
            onNavigateBack()
        }
        Box(Modifier.fillMaxSize()) {
            Text(
                text = "Error: Deal not found",
                modifier = Modifier.align(Alignment.Center)
            )
        }
        return
    }

    // Otherwise, proceed with editing using the non-null deal.
    var dealNumber by remember { mutableStateOf(deal.dealNumber)}
    var opponents by remember { mutableStateOf(deal.opponents) }
    var contract by remember { mutableStateOf(deal.contract) }
    var declarer by remember { mutableStateOf(deal.declarer) }
    var result by remember { mutableStateOf(deal.result) }
    var score by remember { mutableStateOf(deal.score) }
    var notes by remember { mutableStateOf(deal.notes) }

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
                        // Save deal changes
                        viewModel.updateDeal(
                            tournamentId,
                            deal.copy(
                                dealNumber = dealNumber,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedTextField(
                value = dealNumber,
                onValueChange = { dealNumber = it },
                label = { Text(stringResource(R.string.deal_number_label)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
