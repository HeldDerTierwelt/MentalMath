package com.ag.kopfrechner.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ag.kopfrechner.viewmodel.SettingsViewModel

@Composable
fun MyApp() {
    val viewModel = SettingsViewModel()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "settings",
    ) {
        composable("settings") {
            SettingsScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("game") {
            GameScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

