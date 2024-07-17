package com.smg.tokosmg.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smg.tokosmg.data.home.HomeScreen
import com.smg.tokosmg.data.profile.ProfileScreen
import com.smg.tokosmg.repository.AuthRepository
import com.smg.tokosmg.ui.theme.interFontFamily

enum class AuthRoutes {
    Welcome,
    Login,
    Register
}

enum class HomeRoutes {
    Home,
    Beranda,
    Keranjang,
    Transaksi
}

enum class NestedRoutes {
    Main,
    Auth,
    Profile,
}

@Composable
fun Navigation(
    authRepository: AuthRepository = AuthRepository(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = if (!authRepository.hasUser()) NestedRoutes.Auth.name else NestedRoutes.Main.name
    ) {
        authGraph(navController = navController)
        composable(route= NestedRoutes.Main.name) {
            HomeScreen(
                navigateToLogin = {
                    navController.navigate(NestedRoutes.Auth.name){
                        launchSingleTop = true
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                },
                navigateToProfile = {
                    navController.navigate(route="notification")
                }
            )
        }
        composable(
            route = "notification"
        ) {
            ProfileScreen (
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}