package com.example.bridgenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bridgenotes.ui.theme.BridgeNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BridgeNotesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: TournamentViewModel by viewModels()
                    val isLoading by viewModel.isLoading.collectAsState()
                    val tournaments by viewModel.tournaments.collectAsState()
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Column(Modifier.padding(10.dp)) {
                            Text(
                                text = "Tournament list",
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.padding(0.dp, 10.dp)
                            )
                            Box(
                                Modifier.fillMaxSize()
                            ) {
                                if (isLoading) Text(
                                    text = "Loading...", Modifier.align(Center)
                                ) else TournamentList(tournaments)
                            }

                        }

                        FloatingActionButton(
                            onClick = { println("click") }, modifier = Modifier
                                .align(
                                    BottomEnd
                                )
                                .padding(15.dp)
                        ) {
                            Icon(Icons.Filled.Add, "Floating add button")
                        }
                    }


                }
            }
        }
    }
}

@Composable
fun TournamentList(tournaments: List<Tournament>) {
    Column {
        tournaments.forEach { tournament ->
            TournamentRow(tournament)
        }
    }
}

@Composable
fun TournamentRow(tournament: Tournament) {
    Box(
        Modifier
            .padding(0.dp, 5.dp)
            .border(2.dp, Color.Gray)
            .padding(10.dp, 15.dp)
            .fillMaxWidth()
    ) {
        Text(text = tournament.name)
    }

}