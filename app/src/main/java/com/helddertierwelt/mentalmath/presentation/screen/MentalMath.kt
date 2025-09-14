/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.presentation.screen

import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.helddertierwelt.mentalmath.presentation.viewmodel.GameViewModel
import com.helddertierwelt.mentalmath.presentation.viewmodel.SettingsViewModel

@Composable
fun MentalMath(
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

        composable(
            route = "statistics",
            enterTransition = { fadeIn() },
        ) {
            StatisticsScreen(
                settingsViewModel = settingsViewModel,
                gameViewModel = gameViewModel,
                navController = navController
            )
        }
    }
}

