package com.smg.tokosmg.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smg.tokosmg.R
import com.smg.tokosmg.navigation.BottomAppSceen
import com.smg.tokosmg.navigation.HomeNavGraph
import com.smg.tokosmg.ui.theme.Gradient2
import com.smg.tokosmg.ui.theme.interFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    navController : NavHostController
) {
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
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = interFontFamily
                    )
                },
                actions = {
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bell_fill),
                            contentDescription = "notification icon",
                            modifier = Modifier.size(20.dp),
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(shape = CircleShape)
                            .clickable { }
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
                            if (selected) Icon(
                                painter = painterResource(id = screen.selectedIcon),
                                contentDescription = "Icon Selected",
                                modifier = Modifier.size(20.dp)
                            ) else Icon(
                                painter = painterResource(id = screen.unselectedIcon),
                                contentDescription = "Icon Unselected",
                                modifier = Modifier.size(20.dp)
                            )
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
    ) {
        val padding = it
        HomeNavGraph(navController = navController, paddingValues = padding)
    }
}