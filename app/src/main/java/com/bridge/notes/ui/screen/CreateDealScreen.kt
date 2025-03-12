package com.bridge.notes.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Check
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.bridge.notes.R
import com.bridge.notes.model.Deal
import com.bridge.notes.model.TournamentViewModel

private const val PASSED_OUT = "PASSED OUT"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDealScreen(
    tournamentId: Long,
    onNavigateBack: () -> Unit,
    viewModel: TournamentViewModel
) {
    var dealNumber by rememberSaveable { mutableStateOf("") }
    var opponents by rememberSaveable { mutableStateOf("") }
    var contract by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("") }
    var score by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf("") }

    // Add these state variables for contract components
    var contractLevel by rememberSaveable { mutableStateOf("1") }
    var contractSuit by rememberSaveable { mutableStateOf("") }
    var contractDouble by rememberSaveable { mutableStateOf("-") }

    // Add these lists for the dropdown options
    val levels = listOf(PASSED_OUT) + (1..7).map { it.toString() }
    val suits = listOf("♠", "♥", "♦", "♣", "NT")
    val doubles = listOf("-", "X", "XX")
    val declarers = listOf("North", "East", "South", "West")

    val tournaments by viewModel.tournaments.collectAsState()
    val tournament = tournaments.find { it.id == tournamentId }
    val scope = rememberCoroutineScope()

    // Add state tracking for the new deal
    var createdDealId by rememberSaveable { mutableStateOf<Long?>(null) }

    // Change declarer state to initialize with first option
    var declarer by rememberSaveable { mutableStateOf(declarers[0]) }

    // Add effect to monitor deal creation
    LaunchedEffect(createdDealId) {
        if (createdDealId != null) {
            delay(200) // Give time for state to update
            println("Deal creation completed, navigating back")
            onNavigateBack()
        }
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
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
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(
                        onClick = {
                            val newDeal = Deal(
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
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                modifier = Modifier.fillMaxWidth(),
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
                                    if (contractLevel != PASSED_OUT) {
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
                                    if (contractLevel != PASSED_OUT) {
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
