package com.bridge.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.navigation.navArgument
import com.bridge.notes.model.CreateDealViewModel
import com.bridge.notes.model.CreateTournamentViewModel
import com.bridge.notes.model.DealDetailViewModel
import com.bridge.notes.model.TournamentListViewModel
import com.bridge.notes.model.TournamentDetailsViewModel
import com.bridge.notes.model.EditDealViewModel
import com.bridge.notes.model.EditTournamentViewModel
import com.bridge.notes.model.ViewModelFactory
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
    private val tournamentListViewModel: TournamentListViewModel by viewModels {
        ViewModelFactory(
            applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (BuildConfig.INSERT_DEMO_DATA) {
            tournamentListViewModel.insertDemoData()
        }
        super.onCreate(savedInstanceState)
        setContent {
            BridgeNotesTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = TournamentListScreenObj) {
                    composable<TournamentListScreenObj> {
                        TournamentListScreen(
                            viewModel = tournamentListViewModel,
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
                        val tournamentDetailsViewModel: TournamentDetailsViewModel by viewModels {
                            ViewModelFactory(
                                applicationContext
                            )
                        }
                        LaunchedEffect(args.id) {
                            tournamentDetailsViewModel.loadTournament(args.id)
                        }
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
                            viewModel = tournamentDetailsViewModel
                        )
                    }
                    composable(
                        route = "tournament/{tournamentId}/create_deal",
                        arguments = listOf(
                            navArgument("tournamentId") { type = NavType.LongType }
                        )
                    ) { backStackEntry ->
                        val viewModel: CreateDealViewModel by viewModels {
                            ViewModelFactory(
                                applicationContext
                            )
                        }
                        CreateDealScreen(
                            tournamentId = backStackEntry.arguments?.getLong("tournamentId") ?: 0L,
                            onNavigateBack = { navController.navigateUp() },
                            viewModel = viewModel
                        )
                    }
                    composable(
                        route = "tournament/{tournamentId}/deal/{dealId}",
                        arguments = listOf(
                            navArgument("tournamentId") { type = NavType.LongType },
                            navArgument("dealId") { type = NavType.LongType }
                        )
                    ) { backStackEntry ->
                        val viewModel: DealDetailViewModel by viewModels {
                            ViewModelFactory(
                                applicationContext
                            )
                        }
                        val tournamentId = backStackEntry.arguments?.getLong("tournamentId") ?: 0L
                        val dealId = backStackEntry.arguments?.getLong("dealId") ?: 0L
                        LaunchedEffect(tournamentId, dealId) {
                            viewModel.loadDeal(tournamentId, dealId)
                        }
                        DealDetailScreen(
                            dealId = dealId,
                            tournamentId = tournamentId,
                            onNavigateBack = { navController.navigateUp() },
                            onEdit = {
                                navController.navigate("tournament/$tournamentId/deal/$dealId/edit")
                            },
                            viewModel = viewModel
                        )
                    }
                    composable(
                        route = "tournament/{tournamentId}/deal/{dealId}/edit",
                        arguments = listOf(
                            navArgument("tournamentId") { type = NavType.LongType },
                            navArgument("dealId") { type = NavType.LongType }
                        )
                    ) { backStackEntry ->
                        val viewModel: EditDealViewModel by viewModels {
                            ViewModelFactory(
                                applicationContext
                            )
                        }
                        val tournamentId = backStackEntry.arguments?.getLong("tournamentId") ?: 0L
                        val dealId = backStackEntry.arguments?.getLong("dealId") ?: 0L
                        LaunchedEffect(tournamentId, dealId) {
                            viewModel.loadDeal(tournamentId, dealId)
                        }
                        EditDealScreen(
                            dealId = dealId,
                            tournamentId = tournamentId,
                            onNavigateBack = { navController.navigateUp() },
                            viewModel = viewModel
                        )
                    }
                    composable(
                        route = "edit_tournament/{tournamentId}",
                        arguments = listOf(
                            navArgument("tournamentId") { type = NavType.LongType }
                        )
                    ) { backStackEntry ->
                        val viewModel: EditTournamentViewModel by viewModels {
                            ViewModelFactory(
                                applicationContext
                            )
                        }
                        val tournamentId = backStackEntry.arguments?.getLong("tournamentId") ?: 0L
                        LaunchedEffect(tournamentId) {
                            viewModel.loadTournament(tournamentId)
                        }
                        EditTournamentScreen(
                            tournamentId = tournamentId,
                            onNavigateBack = { navController.navigateUp() },
                            viewModel = viewModel
                        )
                    }
                    composable("create_tournament") {
                        val viewModel: CreateTournamentViewModel by viewModels {
                            ViewModelFactory(
                                applicationContext
                            )
                        }
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