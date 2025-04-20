package com.ag.kopfrechner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ag.kopfrechner.ui.screen.MyApp
import com.ag.kopfrechner.ui.theme.MyApplicationTheme
import com.ag.kopfrechner.viewmodel.GameViewModel
import com.ag.kopfrechner.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel = SettingsViewModel()
            val gameViewModel = GameViewModel()
            val navController = rememberNavController()
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