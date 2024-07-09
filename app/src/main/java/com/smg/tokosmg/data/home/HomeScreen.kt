package com.smg.tokosmg.data.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.smg.tokosmg.R
import com.smg.tokosmg.navigation.BottomAppSceen
import com.smg.tokosmg.navigation.BottomNavGraph
import com.smg.tokosmg.ui.theme.interFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    homeViewModel: HomeViewModel = viewModel(HomeViewModel::class.java),
    navigateToLogin: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToNotification: () -> Unit
) {
    val navController = rememberNavController()

    var showMenu by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        homeViewModel.getUser()
    }

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                ),
                title = {
                    Text(
                        text = "Toko Sembako SMG",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontFamily = interFontFamily
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navigateToNotification.invoke()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bell_fill),
                            contentDescription = "notification icon",
                            modifier = Modifier.size(20.dp),
                        )
                    }
                    AsyncImage(
                        model = homeViewModel.currentUser.user.data?.fotoProfil,
                        contentDescription = "Foto Profil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(shape = CircleShape)
                            .clickable {
                                showMenu = !showMenu
                            }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            )
        },
        bottomBar = {
            BottomAppBar (
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val screens = listOf(
                    BottomAppSceen.BerandaScreen,
                    BottomAppSceen.KeranjangScreen,
                    BottomAppSceen.TransaksiScreen
                )

                screens.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    NavigationBarItem(
                        selected = selected,
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (screen.label == "Keranjang") {
                                        if (homeViewModel.currentUser.user.data?.keranjang?.isNotEmpty() == true) {
                                            Badge {
                                                Text(text = homeViewModel.currentUser.user.data?.keranjang?.size.toString())
                                            }
                                        }
                                    }
                                }
                            ) {
                                if (selected) Icon(
                                    painter = painterResource(id = screen.selectedIcon),
                                    contentDescription = "Icon Selected",
                                    modifier = Modifier.size(20.dp)
                                ) else Icon(
                                    painter = painterResource(id = screen.unselectedIcon),
                                    contentDescription = "Icon Unselected",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        },
                        label = {
                            Text(
                                text = screen.label,
                                fontFamily = interFontFamily,
                                fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            navController.navigate(route = screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                                Log.d("selected", "HomeScreen: $selected")
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onSurface,
                            selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        )
                    )
                }
            }
        }
    ) { padding ->
        BottomNavGraph(navController = navController, padding = padding)
        if (showMenu) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.TopEnd
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .shadow(
                            elevation = 2.dp,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    Column (
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = homeViewModel.currentUser.user.data?.nama ?: "",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = interFontFamily
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                homeViewModel.logout()
                                navigateToLogin.invoke()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text(text = "Keluar")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(painter = painterResource(id = R.drawable.box_arrow_right), contentDescription = "", tint = Color.White)
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = homeViewModel.hasUser) {
        if (!homeViewModel.hasUser) {
            navigateToLogin.invoke()
        }
    }
}