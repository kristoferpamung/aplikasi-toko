package com.smg.tokosmg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.smg.tokosmg.ui.screens.HomeScreen
import com.smg.tokosmg.ui.screens.KeranjangScreen
import com.smg.tokosmg.ui.screens.LoginScreen
import com.smg.tokosmg.ui.screens.RegisterScreen
import com.smg.tokosmg.ui.theme.TokoSMGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            TokoSMGTheme {
                HomeScreen(navController)
            }
        }
    }
}