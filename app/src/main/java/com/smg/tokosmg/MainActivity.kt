package com.smg.tokosmg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.smg.tokosmg.ui.screens.RegisterScreen
import com.smg.tokosmg.ui.theme.TokoSMGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TokoSMGTheme {
                RegisterScreen()
            }
        }
    }
}