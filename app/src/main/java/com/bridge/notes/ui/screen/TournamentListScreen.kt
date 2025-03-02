package com.bridge.notes.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bridge.notes.R
import com.bridge.notes.model.Tournament
import com.bridge.notes.model.TournamentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentListScreen(
    viewModel: TournamentViewModel,
    showTournamentDetail: (id: Long) -> Unit,
    onCreateTournament: () -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val tournaments by viewModel.tournaments.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.tournament_list_title))
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateTournament
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_tournament)
                )
            }
        }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(Modifier.padding(10.dp)) {
                Box(
                    Modifier.fillMaxSize()
                ) {
                    if (isLoading) {
                        Text(
                            text = stringResource(R.string.loading),
                            Modifier.align(Center)
                        )
                    } else {
                        TournamentList(tournaments, showTournamentDetail)
                    }
                }
            }
        }
    }
}

@Composable
private fun TournamentList(tournaments: List<Tournament>, showTournamentDetail: (id: Long) -> Unit) {
    LazyColumn {
        items(tournaments) { tournament ->
            TournamentRow(tournament, showTournamentDetail)

        }
    }
}

@Composable
private fun TournamentRow(tournament: Tournament, showTournamentDetail: (id: Long) -> Unit) {
    Box(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 4.dp)
        .fillMaxWidth()
        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.medium)
        .clickable { showTournamentDetail(tournament.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = tournament.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = tournament.date.toLocalDate().toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = tournament.pairOrTeam,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}