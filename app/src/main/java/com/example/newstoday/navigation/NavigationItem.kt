package com.example.newstoday.navigation

import com.example.newstoday.R

sealed class NavigationItem(val title: String, val route: String, var icon: Int) {
    data object Home : NavigationItem("Home", "home", R.drawable.home_icon)
    data object Category : NavigationItem("Category", "category", R.drawable.category_icon)
    data object Favorite : NavigationItem("Favorite", "favorite", R.drawable.favorite_icon)
    data object Account : NavigationItem("Account", "account", R.drawable.person_icon)
}

sealed class NavigationObject(val route: String) {
    data object DetailScreen : NavigationObject("DetailNewsScreen")
    data object LoginScreen : NavigationObject("LoginScreen")
}
