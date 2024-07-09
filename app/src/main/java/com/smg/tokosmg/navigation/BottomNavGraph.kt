package com.smg.tokosmg.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smg.tokosmg.R
import com.smg.tokosmg.data.beranda.BerandaScreen
import com.smg.tokosmg.data.keranjang.KeranjangScreen
import com.smg.tokosmg.data.transaksi.TransaksiScreen

@Composable
fun BottomNavGraph (
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoutes.Beranda.name,
        route = NestedRoutes.Main.name
    ) {
        composable(
            route = HomeRoutes.Beranda.name
        ) {
            BerandaScreen(padding = padding)
        }
        composable(
            route = HomeRoutes.Keranjang.name
        ) {
            KeranjangScreen(padding = padding)
        }
        composable(
            route = HomeRoutes.Transaksi.name
        ) {
            TransaksiScreen(padding = padding)
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
        route = HomeRoutes.Beranda.name,
        label = "Beranda",
        selectedIcon = R.drawable.house_door_fill,
        unselectedIcon = R.drawable.house_door
    )
    object KeranjangScreen: BottomAppSceen(
        route = HomeRoutes.Keranjang.name,
        label = "Keranjang",
        selectedIcon = R.drawable.cart_fill,
        unselectedIcon = R.drawable.cart
    )
    object TransaksiScreen: BottomAppSceen(
        route = HomeRoutes.Transaksi.name,
        label = "Pesanan",
        selectedIcon = R.drawable.bag_check_fill,
        unselectedIcon = R.drawable.bag_check
    )
}