package com.bridge.notes.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bridge.notes.R
import com.bridge.notes.model.Tournament
import com.bridge.notes.model.TournamentListViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentListScreen(
    viewModel: TournamentListViewModel,
    showTournamentDetail: (Long) -> Unit,
    onCreateTournament: () -> Unit
) {
    val tournaments by viewModel.tournaments.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tournaments)) },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateTournament) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.create_tournament))
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(tournaments) { tournament ->
                TournamentItem(
                    tournament = tournament,
                    onClick = { showTournamentDetail(tournament.id) }
                )
            }
        }
    }
}

@Composable
private fun TournamentItem(
    tournament: Tournament,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = tournament.name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = tournament.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = tournament.pairOrTeam,
                style = MaterialTheme.typography.bodyMedium
            )
            if (tournament.note.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = tournament.note,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}