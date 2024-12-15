package com.example.bridgenotes

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentListScreen(
    viewModel: TournamentViewModel, showTournamentDetail: (id: String) -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val tournaments by viewModel.tournaments.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text("Tournament list")
                }, scrollBehavior = scrollBehavior
            )

        }) { innerPadding ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(Modifier.padding(10.dp)) {
                Box(
                    Modifier.fillMaxSize()
                ) {
                    if (isLoading) Text(
                        text = "Loading...", Modifier.align(Center)
                    ) else TournamentList(tournaments, showTournamentDetail)
                }

            }

            FloatingActionButton(
                onClick = { println("click") }, modifier = Modifier
                    .align(BottomEnd)
                    .padding(15.dp)
            ) {
                Icon(Icons.Filled.Add, "Floating add button")
            }
        }

    }
}

@Composable
private fun TournamentList(tournaments: List<Tournament>, showTournamentDetail: (id: String) -> Unit) {
    LazyColumn {
        items(tournaments) { tournament ->
            TournamentRow(tournament, showTournamentDetail)

        }
    }
}

@Composable
private fun TournamentRow(tournament: Tournament, showTournamentDetail: (id: String) -> Unit) {
    Box(modifier = Modifier
        .padding(0.dp, 5.dp)
        .border(2.dp, Color.Gray)
        .padding(10.dp, 15.dp)
        .fillMaxWidth()
        .clickable { showTournamentDetail(tournament.id) }) {
        Text(text = tournament.name)
    }

}