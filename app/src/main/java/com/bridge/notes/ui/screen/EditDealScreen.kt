package com.bridge.notes.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import com.bridge.notes.R
import com.bridge.notes.model.DealViewModel

private const val PASSED_OUT = "PASSED OUT"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDealScreen(
    dealId: Long,
    tournamentId: Long,
    onNavigateBack: () -> Unit,
    viewModel: DealViewModel
) {
    val deal by viewModel.currentDeal.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    LaunchedEffect(dealId, tournamentId) {
        viewModel.loadDeal(tournamentId, dealId)
    }

    var dealNumber by rememberSaveable { mutableStateOf(deal?.dealNumber ?: "") }
    var opponents by rememberSaveable { mutableStateOf(deal?.opponents ?: "") }
    var contract by rememberSaveable { mutableStateOf(deal?.contract ?: "") }
    var result by rememberSaveable { mutableStateOf(deal?.result ?: "") }
    var score by rememberSaveable { mutableStateOf(deal?.score ?: "") }
    var notes by rememberSaveable { mutableStateOf(deal?.notes ?: "") }

    // Add these state variables for contract components
    var contractLevel by rememberSaveable { mutableStateOf("1") }
    var contractSuit by rememberSaveable { mutableStateOf("") }
    var contractDouble by rememberSaveable { mutableStateOf("-") }

    // Add these lists for the dropdown options
    val levels = listOf(PASSED_OUT) + (1..7).map { it.toString() }
    val suits = listOf("♠", "♥", "♦", "♣", "NT")
    val doubles = listOf("-", "X", "XX")
    val declarers = listOf("North", "East", "South", "West")

    // Change declarer state to initialize with first option
    var declarer by rememberSaveable { mutableStateOf(declarers[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_deal)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val updatedDeal = deal?.copy(
                                dealNumber = dealNumber,
                                opponents = opponents,
                                contract = contract,
                                declarer = declarer,
                                result = result,
                                score = score,
                                notes = notes
                            )
                            if (updatedDeal != null) {
                                viewModel.updateDeal(updatedDeal)
                            }
                            onNavigateBack()
                        }
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = stringResource(R.string.save)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = dealNumber,
                    onValueChange = { dealNumber = it },
                    label = { Text(stringResource(R.string.deal_number)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = opponents,
                    onValueChange = { opponents = it },
                    label = { Text(stringResource(R.string.opponents)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = contractLevel,
                        onValueChange = { contractLevel = it },
                        label = { Text("Level") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = contractSuit,
                        onValueChange = { contractSuit = it },
                        label = { Text("Suit") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = contractDouble,
                        onValueChange = { contractDouble = it },
                        label = { Text("Double") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = declarer,
                    onValueChange = { declarer = it },
                    label = { Text(stringResource(R.string.declarer)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = result,
                    onValueChange = { result = it },
                    label = { Text(stringResource(R.string.result)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = score,
                    onValueChange = { score = it },
                    label = { Text(stringResource(R.string.score)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text(stringResource(R.string.notes)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
