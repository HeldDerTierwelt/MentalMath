package com.ag.kopfrechner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.ag.kopfrechner.data.AppDatabase
import com.ag.kopfrechner.data.CsvImporter
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
            val db = AppDatabase.getDatabase(context)
            val dao = db.mathTaskDao()
            val csvImporter = CsvImporter(applicationContext, dao)

            LaunchedEffect(Unit) {
                csvImporter.importCsvIfNeeded()
            }

            val gameViewModel: GameViewModel = viewModel(
                factory = GameViewModelFactory(dao)
            )

            MyApplicationTheme {
                MyApp(
                    settingsViewModel = settingsViewModel,
                    gameViewModel = gameViewModel,
                    navController = navController
                )
            }
        }
    }
}