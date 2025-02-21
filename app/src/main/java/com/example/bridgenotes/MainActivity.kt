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
                        TournamentListScreen(
                            viewModel = viewModel,
                            showTournamentDetail = { tournamentId ->
                                navController.navigate(
                                    TournamentDetailsScreenParams(tournamentId)
                                )
                            },
                            onCreateTournament = {
                                navController.navigate("create_tournament")
                            }
                        )
                    }
                    composable<TournamentDetailsScreenParams> {
                        val args = it.toRoute<TournamentDetailsScreenParams>()
                        TournamentDetailsScreen(
                            tournamentId = args.id,
                            onNavigateUp = { navController.navigateUp() },
                            onEditTournament = { tournamentId ->
                                navController.navigate("edit_tournament/$tournamentId")
                            },
                            onCreateDeal = {
                                navController.navigate(
                                    "create_deal/${args.id}"
                                )
                            },
                            onShowDealDetail = { dealId ->
                                navController.navigate("deal_detail/$dealId")  // Navigate to deal detail
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
                    composable(
                        route = "deal_detail/{dealId}",
                        arguments = listOf(
                            navArgument("dealId") { type = StringType }
                        )
                    ) { backStackEntry ->
                        val dealId = backStackEntry.arguments?.getString("dealId") ?: ""
                        DealDetailScreen(
                            dealId = dealId,
                            onNavigateBack = { navController.navigateUp() },
                             onEdit = { navController.navigate("edit_deal/$dealId") }
                        )
                    }
                    composable(
                        route = "edit_deal/{dealId}",
                        arguments = listOf(
                            navArgument("dealId") { type = StringType }
                        )
                    ) { backStackEntry ->
                        EditDealScreen(
                            dealId = backStackEntry.arguments?.getString("dealId") ?: "",
                            onNavigateBack = { navController.navigateUp() }
                        )
                    }
                    composable(
                        route = "edit_tournament/{tournamentId}",
                        arguments = listOf(
                            navArgument("tournamentId") { type = StringType }
                        )
                    ) { backStackEntry ->
                        EditTournamentScreen(
                            tournamentId = backStackEntry.arguments?.getString("tournamentId") ?: "",
                            onNavigateBack = { navController.navigateUp() }
                        )
                    }
                    composable("create_tournament") {
                        CreateTournamentScreen(
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