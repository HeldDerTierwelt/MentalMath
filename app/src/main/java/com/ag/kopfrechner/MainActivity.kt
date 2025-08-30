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
import com.ag.kopfrechner.data.MathTaskDao
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
            var mathDao by remember { mutableStateOf<MathTaskDao?>(null) }

            LaunchedEffect(Unit) {
                val db = dbImporter.importDb()
                mathDao = db.mathTaskDao()
            }

            if (mathDao != null) {
                val gameViewModel: GameViewModel = viewModel(
                    factory = GameViewModelFactory(mathDao!!)
                )

                MyApplicationTheme {
                    MyApp(
                        settingsViewModel = settingsViewModel,
                        gameViewModel = gameViewModel,
                        navController = navController
                    )
                }
            } else {
                Text("Lade Aufgaben…")
            }
        }
    }
}