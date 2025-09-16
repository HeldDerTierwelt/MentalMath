/*
Mental Math - Android app for practicing mental arithmetic
Copyright (C) 2025 HeldDerTierwelt

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see https://www.gnu.org/licenses/gpl-3.0.md.
*/

package com.helddertierwelt.mentalmath.presentation.screen

import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.helddertierwelt.mentalmath.presentation.viewmodel.GameViewModel
import com.helddertierwelt.mentalmath.presentation.viewmodel.SettingsViewModel

@Composable
fun Navigation(
    settingsViewModel: SettingsViewModel,
    gameViewModel: GameViewModel,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                enterTransition = { slideInHorizontally(initialOffsetX = { it }) }
            ) {
                GameScreen(
                    gameViewModel = gameViewModel,
                    settingsViewModel = settingsViewModel,
                    navController = navController
                )
            }

            composable(
                route = "statistics",
                enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            ) {
                StatisticsScreen(
                    settingsViewModel = settingsViewModel,
                    gameViewModel = gameViewModel,
                    navController = navController
                )
            }
        }
    }
}

