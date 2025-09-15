/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

/*
 * Copyright (C) 2025 HeldDerTierwelt
 *
 * This file is part of Mental Math.
 *
 * Mental Math is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mental Math is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mental Math. If not, see <https://www.gnu.org/licenses/>.
 */

package com.helddertierwelt.mentalmath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.helddertierwelt.mentalmath.data.dao.AdditionTaskDao
import com.helddertierwelt.mentalmath.data.dao.DivisionTaskDao
import com.helddertierwelt.mentalmath.data.dao.MultiplicationTaskDao
import com.helddertierwelt.mentalmath.data.dao.SubtractionTaskDao
import com.helddertierwelt.mentalmath.data.db.DbImporter
import com.helddertierwelt.mentalmath.presentation.screen.Navigation
import com.helddertierwelt.mentalmath.presentation.theme.MentalMathTheme
import com.helddertierwelt.mentalmath.presentation.theme.ThemeMode
import com.helddertierwelt.mentalmath.presentation.viewmodel.GameViewModel
import com.helddertierwelt.mentalmath.presentation.viewmodel.GameViewModelFactory
import com.helddertierwelt.mentalmath.presentation.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val navController = rememberNavController()
            val context = applicationContext
            val dbImporter = DbImporter(context)
            var additionDao by remember { mutableStateOf<AdditionTaskDao?>(null) }
            var subtractionDao by remember { mutableStateOf<SubtractionTaskDao?>(null) }
            var multiplicationDao by remember { mutableStateOf<MultiplicationTaskDao?>(null) }
            var divisionDao by remember { mutableStateOf<DivisionTaskDao?>(null) }

            LaunchedEffect(Unit) {
                val db = dbImporter.importDb()
                additionDao = db.additionTaskDao()
                subtractionDao = db.subtractionTaskDao()
                multiplicationDao = db.multiplicationTaskDao()
                divisionDao = db.divisionTaskDao()
            }

            if (additionDao != null && subtractionDao != null && multiplicationDao != null && divisionDao != null) {
                val gameViewModel: GameViewModel = viewModel(
                    factory = GameViewModelFactory(
                        additionDao!!,
                        subtractionDao!!,
                        multiplicationDao!!,
                        divisionDao!!
                    )
                )
                lifecycle.addObserver(gameViewModel)
                MentalMathTheme(themeMode = settingsViewModel.settingsState.value.themeMode) {
                    val darkTheme = when (settingsViewModel.settingsState.value.themeMode) {
                        ThemeMode.DARK -> true
                        ThemeMode.LIGHT -> false
                        ThemeMode.SYSTEM -> isSystemInDarkTheme()
                    }
                    val systemUiController = rememberSystemUiController()
                    val navBarColor = MaterialTheme.colorScheme.background

                    SideEffect {
                        systemUiController.setNavigationBarColor(
                            color = navBarColor,
                            darkIcons = !darkTheme
                        )
                        systemUiController.setStatusBarColor(
                            color = Color.Transparent,
                            darkIcons = !darkTheme
                        )
                    }

                    Navigation(
                        settingsViewModel = settingsViewModel,
                        gameViewModel = gameViewModel,
                        navController = navController
                    )
                }
            } else {
                Text("Load math exercise database")
            }
        }
    }
}