package com.smg.tokosmg.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smg.tokosmg.R
import com.smg.tokosmg.ui.screens.BernadaScreen
import com.smg.tokosmg.ui.screens.KeranjangScreen
import com.smg.tokosmg.ui.screens.TransaksiScreen

@Composable
fun HomeNavGraph (
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomAppSceen.BerandaScreen.route
    ) {
        composable(route = BottomAppSceen.BerandaScreen.route) {
            BernadaScreen(paddingValues = paddingValues)
        }
        composable(route = BottomAppSceen.KeranjangScreen.route) {
            KeranjangScreen()
        }
        composable(route = BottomAppSceen.TransaksiScreen.route) {
            TransaksiScreen()
        }
    }
}

sealed class BottomAppSceen (
    val route: String,
    val label: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
) {
    object BerandaScreen: BottomAppSceen(
        route = "BERANDA",
        label = "Beranda",
        selectedIcon = R.drawable.house_door_fill,
        unselectedIcon = R.drawable.house_door
    )
    object KeranjangScreen: BottomAppSceen(
        route = "KERANJANG",
        label = "Keranjang",
        selectedIcon = R.drawable.cart_fill,
        unselectedIcon = R.drawable.cart
    )
    object TransaksiScreen: BottomAppSceen(
        route = "TRANSAKSI",
        label = "Transaksi",
        selectedIcon = R.drawable.bag_check_fill,
        unselectedIcon = R.drawable.bag_check
    )
}