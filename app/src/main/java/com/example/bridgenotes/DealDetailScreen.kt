package com.example.bridgenotes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealDetailScreen(
    dealId: String,
    tournamentId: String,
    onNavigateBack: () -> Unit,
    onEdit: () -> Unit,
    viewModel: TournamentViewModel = viewModel()
) {
    val tournament by viewModel.getTournamentById(tournamentId).collectAsState(initial = null)
    val deal by viewModel.getDealById(tournamentId, dealId).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(tournament?.name ?: "", style = MaterialTheme.typography.titleSmall)
                        Text(
                            stringResource(R.string.deal_number, dealId),
                        )
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
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit_deal)
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
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LabeledField(stringResource(R.string.deal_opponents_label), deal!!.opponents)
                LabeledField(stringResource(R.string.deal_contract_label), "${deal!!.contract} ${deal!!.declarer}")
                LabeledField(stringResource(R.string.deal_result_label), deal!!.result)
                LabeledField(stringResource(R.string.deal_score_label), deal!!.score)
                LabeledField(stringResource(R.string.deal_notes_label), deal!!.notes)
            }
        }
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