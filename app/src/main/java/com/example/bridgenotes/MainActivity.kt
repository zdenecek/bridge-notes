package com.example.bridgenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.navigation.navArgument
import androidx.navigation.NavType.Companion.StringType
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
                            },
                            onCreateDeal = {
                                navController.navigate(
                                    "create_deal/${args.id}"  // You'll need to define this route
                                )
                            }
                        )
                    }
                    composable(
                        route = "create_deal/{tournamentId}",
                        arguments = listOf(
                            navArgument("tournamentId") { type = StringType }
                        )
                    ) { backStackEntry ->
                        CreateDealScreen(
                            tournamentId = backStackEntry.arguments?.getString("tournamentId") ?: "",
                            onNavigateBack = { navController.navigateUp() }
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