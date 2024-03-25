package com.example.newstoday.presentation.theme.bottom_nav_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.newstoday.navigation.NavigationItem

@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Category,
        NavigationItem.Favorite,
        NavigationItem.Account
    )
    NavigationBar(
        containerColor = Color(255, 255, 255),
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                selected = selectedItem == index,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color(172, 175, 195),
                    unselectedTextColor = Color(172, 175, 195),
                    selectedIconColor = Color(71, 90, 215),
                    selectedTextColor = Color(71, 90, 215)
                ),
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}