package com.example.bridgenotes

import Deal
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentDetailsScreen(
    tournamentId: Long,
    onNavigateUp: () -> Unit,
    onEditTournament: (Long) -> Unit,
    onCreateDeal: () -> Unit,
    onShowDealDetail: (Long) -> Unit,
    viewModel: TournamentViewModel
) {
    val tournament by viewModel.tournaments.collectAsState()
        .value.find { it.id == tournamentId }
        .let { remember(it) { mutableStateOf(it) } }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(tournament?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { tournament?.id?.let { onEditTournament(it) } }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit_tournament)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateDeal
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_deal)
                )
            }
        }
    ) { innerPadding ->
        if (tournament == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                // Tournament details
                Text(
                    text = tournament?.date?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) ?: "",
                    style = MaterialTheme.typography.bodyLarge
                )
                if (!tournament?.resultsLink.isNullOrBlank()) {
                    Text(
                        text = tournament?.resultsLink ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            // Handle link click
                        }
                    )
                }
                Text(
                    text = tournament?.pairOrTeam ?: "",
                    style = MaterialTheme.typography.bodyLarge
                )
                if (!tournament?.note.isNullOrBlank()) {
                    Text(
                        text = tournament?.note ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Deals list
                Text(
                    text = stringResource(R.string.deals),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                LazyColumn {
                    items(tournament?.deals ?: emptyList()) { deal ->
                        DealItem(
                            deal = deal,
                            onClick = { onShowDealDetail(deal.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DealItem(
    deal: Deal,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "#${deal.dealNumber}",
            Modifier.width(24.dp)
        )
        Column {
            Text(deal.opponents)
            Text(deal.contract)
            Text(deal.notes)
        }
    }
}