package com.bridge.notes.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bridge.notes.R
import com.bridge.notes.model.TournamentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealDetailScreen(
    dealId: Long,
    tournamentId: Long,
    onNavigateBack: () -> Unit,
    onEdit: () -> Unit,
    viewModel: TournamentViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        println("DealDetailScreen initial load")
        viewModel.loadTournamentAndDeal(tournamentId, dealId)
    }

    val tournaments by viewModel.tournaments.collectAsState()
    val tournament = tournaments.find { it.id == tournamentId }
    val deal = tournament?.deals?.find { it.id == dealId }

    LaunchedEffect(tournaments) {
        println("DealDetailScreen state update")
        println("Current tournaments state: ${tournaments.map { "${it.id}: ${it.deals.map { d -> d.id }}" }}")
        println("Looking for deal: $dealId in tournament: $tournamentId")
        println("Found tournament: ${tournament?.name}")
        println("Tournament deals: ${tournament?.deals?.map { it.id }}")
    }

    val showDeleteConfirmation = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(tournament?.name ?: "", style = MaterialTheme.typography.titleSmall)
                        deal?.let { currentDeal ->
                            Text(
                                text = "Deal #${currentDeal.dealNumber}",
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                actions = {
                    if (deal != null) {
                        IconButton(onClick = { showDeleteConfirmation.value = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_deal)
                            )
                        }
                        IconButton(onClick = onEdit) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(R.string.edit_deal)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (deal == null) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    deal?.let { currentDeal ->
                        LabeledField(stringResource(R.string.deal_opponents_label), currentDeal.opponents)
                        LabeledField(stringResource(R.string.deal_contract_label), "${currentDeal.contract} ${currentDeal.declarer}")
                        LabeledField(stringResource(R.string.deal_result_label), currentDeal.result)
                        LabeledField(stringResource(R.string.deal_score_label), currentDeal.score)
                        LabeledField(stringResource(R.string.deal_notes_label), currentDeal.notes)
                    }
                }
            }
        }
    }

    if (showDeleteConfirmation.value) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation.value = false },
            title = { Text(stringResource(R.string.delete_deal_title)) },
            text = { Text(stringResource(R.string.delete_deal_confirmation)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        deal?.let { viewModel.deleteDeal(it) }
                        showDeleteConfirmation.value = false
                        onNavigateBack()
                    }
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation.value = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
private fun LabeledField(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
} 