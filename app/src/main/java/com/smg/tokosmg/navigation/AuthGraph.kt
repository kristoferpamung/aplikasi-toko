package com.smg.tokosmg.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.smg.tokosmg.data.login.LoginScreen
import com.smg.tokosmg.data.register.RegisterScreen
import com.smg.tokosmg.data.welcome.WelcomeScreen

fun NavGraphBuilder.authGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = AuthRoutes.Welcome.name,
        route = NestedRoutes.Auth.name
    ) {
        composable(route = AuthRoutes.Welcome.name) {
            WelcomeScreen(
                navigateToLogin = {
                    navController.navigate(route = AuthRoutes.Login.name) {
                        launchSingleTop = true
                        popUpTo(route = AuthRoutes.Welcome.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = AuthRoutes.Login.name) {
            LoginScreen(
                navigateToHome = {
                    navController.navigate(route = NestedRoutes.Main.name) {
                        navController.popBackStack()
                        navController.navigate(NestedRoutes.Main.name)
                    }
                },
                navigateToRegister = {
                    navController.navigate(route = AuthRoutes.Register.name) {
                        launchSingleTop = true
                        popUpTo(route = AuthRoutes.Login.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = AuthRoutes.Register.name) {
            RegisterScreen(
                navigateToHome = {
                    navController.navigate(route = NestedRoutes.Main.name) {
                        navController.popBackStack()
                        navController.navigate(NestedRoutes.Main.name)
                    }
                },
                navigateToLogin = {
                    navController.navigate(route= AuthRoutes.Login.name)
                }
            )
        }
    }
}

@Composable
fun WelcomeScreenCoba (
    navigateToLogin: () -> Unit
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navigateToLogin.invoke() }
        ) {
            Text(text = "ke Login")
        }
    }
}