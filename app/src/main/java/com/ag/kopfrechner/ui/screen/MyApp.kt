package com.ag.kopfrechner.ui.screen

import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ag.kopfrechner.viewmodel.GameViewModel
import com.ag.kopfrechner.viewmodel.SettingsViewModel

@Composable
fun MyApp(
    settingsViewModel: SettingsViewModel,
    gameViewModel: GameViewModel,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "settings",
    ) {
        composable(
            route = "settings",
            enterTransition = { fadeIn() },
        ) {
            SettingsScreen(
                settingsViewModel = settingsViewModel,
                gameViewModel = gameViewModel,
                navController = navController
            )

        }
        composable(
            route = "game",
            enterTransition = { fadeIn() }
        ) {
            GameScreen(
                gameViewModel = gameViewModel,
                settingsViewModel = settingsViewModel,
                navController = navController
            )
        }
    }
}

