package com.smg.tokosmg.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.profileGraph (
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