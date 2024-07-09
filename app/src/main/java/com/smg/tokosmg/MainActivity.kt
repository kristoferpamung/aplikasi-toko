package com.smg.tokosmg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smg.tokosmg.data.login.LoginViewModel
import com.smg.tokosmg.data.register.RegisterViewModel
import com.smg.tokosmg.navigation.Navigation
import com.smg.tokosmg.repository.AuthRepository
import com.smg.tokosmg.ui.theme.TokoSMGTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            TokoSMGTheme {
                Navigation()
            }
        }
    }
}