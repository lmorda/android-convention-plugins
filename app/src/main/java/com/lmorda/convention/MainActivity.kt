package com.lmorda.convention

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lmorda.explore.ExploreScreen
import com.lmorda.explore.ExploreViewModel
import com.lmorda.design.theme.ConventionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConventionTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        NavHost(
            navController,
            startDestination = "explore",
        ) {
            composable("explore") {
                val viewModel: ExploreViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()
                ExploreScreen(state)
            }
        }
    }
}
