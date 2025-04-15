package com.ag.kopfrechner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import com.ag.kopfrechner.ui.screen.SettingsScreen
import com.ag.kopfrechner.ui.screen.MyApp
import com.ag.kopfrechner.ui.theme.MyApplicationTheme
import com.ag.kopfrechner.viewmodel.SettingsViewModel


class MainActivity : ComponentActivity() {
    private val viewModel: SettingsViewModel = SettingsViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MyApp { padding ->
                    SettingsScreen(viewModel, androidx.compose.ui.Modifier.padding(padding))
                }
            }
        }
    }
}