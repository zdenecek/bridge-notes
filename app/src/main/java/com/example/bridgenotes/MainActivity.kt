package com.example.bridgenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bridgenotes.ui.theme.BridgeNotesTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BridgeNotesTheme {
                val viewModel: TournamentViewModel by viewModels()
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = TournamentListScreenObj) {
                    composable<TournamentListScreenObj> {
                        TournamentListScreen(viewModel) { tournamentId ->
                            navController.navigate(
                                TournamentDetailsScreenParams(tournamentId)
                            )
                        }
                    }
                    composable<TournamentDetailsScreenParams> {
                        val args = it.toRoute<TournamentDetailsScreenParams>()
                        TournamentDetailsScreen(
                            tournamentId = args.id,
                            onNavigateUp = { navController.navigateUp() },
                            onEditResult = { resultId -> 
                                // TODO: Implement navigation to edit result screen
                            }
                        )
                    }
                }
            }
        }
    }
}

@Serializable
private object TournamentListScreenObj

@Serializable
private data class TournamentDetailsScreenParams(
    val id: String
)