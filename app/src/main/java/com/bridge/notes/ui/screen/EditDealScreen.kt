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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bridge.notes.R
import com.bridge.notes.model.TournamentViewModel

private const val PASSED_OUT = "PASSED OUT"

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
    var dealNumber by rememberSaveable { mutableStateOf(deal.dealNumber)}
    var opponents by rememberSaveable { mutableStateOf(deal.opponents) }
    var contract by rememberSaveable { mutableStateOf(deal.contract) }
    var declarer by rememberSaveable { mutableStateOf(deal.declarer) }
    var result by rememberSaveable { mutableStateOf(deal.result) }
    var score by rememberSaveable { mutableStateOf(deal.score) }
    var notes by rememberSaveable { mutableStateOf(deal.notes) }

    // Add these lists for the dropdown options
    val levels = listOf(PASSED_OUT) + (1..7).map { it.toString() }
    val suits = listOf("♠", "♥", "♦", "♣", "NT")
    val doubles = listOf("-", "X", "XX")
    val declarers = listOf("North", "East", "South", "West")

    // Split the contract into components
    var contractLevel by rememberSaveable {
        mutableStateOf(if (deal.contract == PASSED_OUT) PASSED_OUT else deal.contract.firstOrNull()?.toString() ?: "1")
    }
    var contractSuit by rememberSaveable {
        mutableStateOf(if (deal.contract == PASSED_OUT) "" else deal.contract.substring(1).takeWhile { !it.isWhitespace() })
    }
    var contractDouble by rememberSaveable {
        mutableStateOf(if (deal.contract == PASSED_OUT) "" else deal.contract.substringAfterLast(" ", "-"))
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

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
                scrollBehavior = scrollBehavior,
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
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

             Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var levelExpanded by rememberSaveable { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = levelExpanded,
                    onExpandedChange = { levelExpanded = !levelExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = contractLevel,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Level") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = levelExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = levelExpanded,
                        onDismissRequest = { levelExpanded = false }
                    ) {
                        levels.forEach { level ->
                            DropdownMenuItem(
                                text = { Text(level) },
                                onClick = { 
                                    contractLevel = level
                                    contract = if (level == PASSED_OUT) {
                                        PASSED_OUT
                                    } else {
                                        "$level$contractSuit $contractDouble"
                                    }
                                    levelExpanded = false
                                }
                            )
                        }
                    }
                }

                var suitExpanded by rememberSaveable { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = suitExpanded,
                    onExpandedChange = { if (contractLevel != PASSED_OUT) suitExpanded = !suitExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = contractSuit,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Suit") },
                        enabled = contractLevel != PASSED_OUT,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = suitExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = suitExpanded,
                        onDismissRequest = { suitExpanded = false }
                    ) {
                        suits.forEach { suit ->
                            DropdownMenuItem(
                                text = { Text(suit) },
                                onClick = { 
                                    contractSuit = suit
                                    if (contractLevel != PASSED_OUT)
                                    {
                                        contract = "$contractLevel$contractSuit $contractDouble"
                                    }
                                    suitExpanded = false
                                }
                            )
                        }
                    }
                }

                var doubleExpanded by rememberSaveable { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = doubleExpanded,
                    onExpandedChange = { if (contractLevel != PASSED_OUT) doubleExpanded = !doubleExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = contractDouble,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Double") },
                        enabled = contractLevel != PASSED_OUT,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = doubleExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = doubleExpanded,
                        onDismissRequest = { doubleExpanded = false }
                    ) {
                        doubles.forEach { double ->
                            DropdownMenuItem(
                                text = { Text(double) },
                                onClick = { 
                                    contractDouble = double
                                    if (contractLevel != PASSED_OUT)
                                    {
                                      contract = "$contractLevel$contractSuit $double"
                                    }
                                    doubleExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            var declarerExpanded by rememberSaveable { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = declarerExpanded,
                onExpandedChange = { if (contractLevel != PASSED_OUT) declarerExpanded = !declarerExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = declarer,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.deal_declarer_label)) },
                    enabled = contractLevel != PASSED_OUT,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = declarerExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = declarerExpanded,
                    onDismissRequest = { declarerExpanded = false }
                ) {
                    declarers.forEach { direction ->
                        DropdownMenuItem(
                            text = { Text(direction) },
                            onClick = { 
                                declarer = direction
                                declarerExpanded = false
                            }
                        )
                    }
                }
            }

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
