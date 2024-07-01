package com.smg.tokosmg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smg.tokosmg.ui.screens.HomeScreen

@Composable
fun RootNavGraph (navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Graph.AUTHENTICATION,
        route = Graph.ROOT,
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            HomeScreen(navController = navController)
        }
    }
}