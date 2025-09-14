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

package com.ag.kopfrechner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.ag.kopfrechner.data.DbImporter
import com.ag.kopfrechner.data.dao.AdditionTaskDao
import com.ag.kopfrechner.data.dao.DivisionTaskDao
import com.ag.kopfrechner.data.dao.MultiplicationTaskDao
import com.ag.kopfrechner.data.dao.SubtractionTaskDao
import com.ag.kopfrechner.ui.screen.MyApp
import com.ag.kopfrechner.ui.theme.MyApplicationTheme
import com.ag.kopfrechner.viewmodel.GameViewModel
import com.ag.kopfrechner.viewmodel.GameViewModelFactory
import com.ag.kopfrechner.viewmodel.SettingsViewModel

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
                MyApplicationTheme(themeMode = settingsViewModel.settingsState.value.themeMode) {
                    MyApp(
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