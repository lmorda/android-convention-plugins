package com.lmorda.convention

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lmorda.explore.details.DetailsScreen
import com.lmorda.explore.details.DetailsViewModel
import com.lmorda.explore.repos.ExploreScreen
import com.lmorda.explore.repos.ExploreViewModel

const val routeExplore = "explore"
const val routeDetailsBase = "details"
const val argDetailsId = "id"
const val routeDetailsFull = "$routeDetailsBase/{$argDetailsId}"

@Composable
internal fun ConventionNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = routeExplore,
    ) {
        composable(route = routeExplore) {
            val viewModel: ExploreViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            ExploreScreen(
                state = state,
                onNavigateToDetails = { id ->
                    navController.navigate("$routeDetailsBase/$id")
                }
            )
        }
        composable(
            route = routeDetailsFull,
            arguments = listOf(
                navArgument(name = argDetailsId) { type = NavType.LongType },
            )
        ) {
            val viewModel: DetailsViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            DetailsScreen(
                state = state,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
