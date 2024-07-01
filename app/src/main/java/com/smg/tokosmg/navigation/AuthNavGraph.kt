package com.smg.tokosmg.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smg.tokosmg.ui.screens.LoginScreen
import com.smg.tokosmg.ui.screens.RegisterScreen
import com.smg.tokosmg.ui.screens.WelcomeScreen

fun NavGraphBuilder.authNavGraph (
    navController : NavHostController
) {
    navigation(
        startDestination = AuthScreen.WelcomeScreen.route,
        route = Graph.AUTHENTICATION
    ) {
        composable(
            route = AuthScreen.WelcomeScreen.route
        ) {
            WelcomeScreen()
        }
        composable(
            route = AuthScreen.LoginScreen.route
        ) {
            LoginScreen()
        }
        composable(
            route = AuthScreen.RegisterScreen.route
        ) {
            RegisterScreen()
        }
    }
}

sealed class AuthScreen(val route: String) {
    object LoginScreen : AuthScreen(route = "LOGIN")
    object RegisterScreen : AuthScreen(route = "REGISTER")
    object WelcomeScreen : AuthScreen(route = "WELCOME")
}