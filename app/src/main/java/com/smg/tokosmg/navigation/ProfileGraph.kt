package com.smg.tokosmg.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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

fun NavGraphBuilder.profileGraph (
    navController: NavHostController
) {
    navigation(
        startDestination = HomeRoutes.Home.name,
        route = NestedRoutes.Profile.name
    ){
        composable(
            route = NestedRoutes.Profile.name
        ) {

        }
    }
}