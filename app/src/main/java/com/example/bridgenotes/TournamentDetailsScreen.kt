package com.example.bridgenotes

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentDetailsScreen(
    tournamentId: String, 
    onNavigateUp: () -> Unit,
    onEditTournament: (String) -> Unit,
    onEditResult: (String) -> Unit = {},
    onCreateDeal: () -> Unit = {},
    onShowDealDetail: (String) -> Unit = {}
) {
    val mockResults = remember {
        listOf(
            TournamentResult("1", "NS - vs. Novotná - Novotný", "2♣ N +1 140"),
            TournamentResult("2", "NS - vs. Novotná - Novotný", "2♣ E -1 +100", "moje poznámka"),
            TournamentResult("3", "NS - vs. Novotná - Novotný", "2NT E +1 -150"),
            TournamentResult("4", "NS - vs. Pekarová - Adamová", "2NT E +1 -150"),
            // ... add more results as needed
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Velká cena Prahy - 1. kolo") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onEditTournament(tournamentId) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit tournament"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateDeal) {
                Text("+")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Column(Modifier.padding(16.dp)) {
                    Text("Datum konání")
                    Text("19. 10. 2024")
                    Spacer(Modifier.height(16.dp))
                    
                    Text("Odkaz na výsledky")
                    Text("https://vysledky.bkpraha.cz/prezentace/2024/cbt-praha...")
                    Spacer(Modifier.height(16.dp))

                    Text("Pýr / Tým")
                    Text("Kaštovský - Tomis za tým Trefil")
                    Spacer(Modifier.height(16.dp))

                    Text("Poznámka")
                    Text("Máma mele maso. Máma mele maso. Máma mele maso. Máma mele maso. Máma mele maso.")


                }
            }
            
            items(mockResults) { result ->
                ResultItem(result, onEditResult, onShowDealDetail)
            }
        }
    }
}

@Composable
private fun ResultItem(
    result: TournamentResult,
    onEditResult: (String) -> Unit,
    onShowDealDetail: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onShowDealDetail(result.id) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(result.id, Modifier.width(24.dp))
        Column {
            Text(result.opponents)
            Text(result.contract)
            result.note?.let { Text(it) }
        }
    }
}

data class TournamentResult(
    val id: String,
    val opponents: String,
    val contract: String,
    val note: String? = null
)