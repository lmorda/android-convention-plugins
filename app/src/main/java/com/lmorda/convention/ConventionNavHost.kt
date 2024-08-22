package com.lmorda.convention

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lmorda.explore.ExploreScreen
import com.lmorda.explore.ExploreViewModel

const val destinationExplore = "Explore"

@Composable
internal fun ConventionNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = destinationExplore,
    ) {
        composable(route = destinationExplore) {
            val viewModel: ExploreViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            ExploreScreen(state)
        }
    }
}
