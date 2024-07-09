package com.smg.tokosmg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smg.tokosmg.data.home.HomeScreen
import com.smg.tokosmg.repository.AuthRepository

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
                navigateToProfile = { }
            )
        }
    }
}

