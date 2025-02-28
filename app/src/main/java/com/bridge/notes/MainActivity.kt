package com.bridge.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.navigation.navArgument
import com.bridge.notes.model.TournamentViewModel
import com.bridge.notes.model.TournamentViewModelFactory
import com.bridge.notes.ui.screen.CreateDealScreen
import com.bridge.notes.ui.screen.CreateTournamentScreen
import com.bridge.notes.ui.screen.DealDetailScreen
import com.bridge.notes.ui.screen.EditDealScreen
import com.bridge.notes.ui.screen.EditTournamentScreen
import com.bridge.notes.ui.screen.TournamentDetailsScreen
import com.bridge.notes.ui.screen.TournamentListScreen
import com.bridge.notes.ui.theme.BridgeNotesTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    private val viewModel: TournamentViewModel by viewModels { TournamentViewModelFactory(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (BuildConfig.INSERT_DEMO_DATA){
            viewModel.insertDemoData()
        }
        super.onCreate(savedInstanceState)
        setContent {
            BridgeNotesTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = TournamentListScreenObj) {
                    composable<TournamentListScreenObj> {
                        TournamentListScreen(
                            viewModel = viewModel,  // Pass the activity-level viewModel
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
                                    "tournament/${args.id}/create_deal"
                                )
                            },
                            onShowDealDetail = { dealId ->
                                navController.navigate("tournament/${args.id}/deal/$dealId")
                            },
                            viewModel = viewModel  // Pass the activity-level viewModel
                        )
                    }
                    composable(
                        route = "tournament/{tournamentId}/create_deal",
                        arguments = listOf(
                            navArgument("tournamentId") { type = NavType.LongType }
                        )
                    ) { backStackEntry ->
                        CreateDealScreen(
                            tournamentId = backStackEntry.arguments?.getLong("tournamentId") ?: 0L,
                            onNavigateBack = { navController.navigateUp() },
                            viewModel = viewModel  // Pass the activity-level viewModel
                        )
                    }
                    composable(
                        route = "tournament/{tournamentId}/deal/{dealId}",
                        arguments = listOf(
                            navArgument("tournamentId") { type = NavType.LongType },
                            navArgument("dealId") { type = NavType.LongType }
                        )
                    ) { backStackEntry ->
                        DealDetailScreen(
                            dealId = backStackEntry.arguments?.getLong("dealId") ?: 0L,
                            tournamentId = backStackEntry.arguments?.getLong("tournamentId") ?: 0L,
                            onNavigateBack = { navController.navigateUp() },
                            onEdit = { 
                                val tId = backStackEntry.arguments?.getLong("tournamentId") ?: 0L
                                val dId = backStackEntry.arguments?.getLong("dealId") ?: 0L
                                navController.navigate("tournament/$tId/deal/$dId/edit") 
                            },
                            viewModel = viewModel  // Pass the activity-level viewModel
                        )
                    }
                    composable(
                        route = "tournament/{tournamentId}/deal/{dealId}/edit",
                        arguments = listOf(
                            navArgument("tournamentId") { type = NavType.LongType },
                            navArgument("dealId") { type = NavType.LongType }
                        )
                    ) { backStackEntry ->
                        val dealId = backStackEntry.arguments?.getLong("dealId") ?: 0L
                        EditDealScreen(
                            dealId = dealId,
                            tournamentId = backStackEntry.arguments?.getLong("tournamentId") ?: 0L,
                            onNavigateBack = { navController.navigateUp() },
                            viewModel = viewModel  // Pass the activity-level viewModel
                        )
                    }
                    composable(
                        route = "edit_tournament/{tournamentId}",
                        arguments = listOf(
                            navArgument("tournamentId") { type = NavType.LongType }
                        )
                    ) { backStackEntry ->
                        EditTournamentScreen(
                            tournamentId = backStackEntry.arguments?.getLong("tournamentId") ?: 0L,
                            onNavigateBack = { navController.navigateUp() },
                            viewModel = viewModel
                        )
                    }
                    composable("create_tournament") {
                        CreateTournamentScreen(
                            onNavigateBack = { navController.navigateUp() },
                            viewModel = viewModel
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
    val id: Long
)