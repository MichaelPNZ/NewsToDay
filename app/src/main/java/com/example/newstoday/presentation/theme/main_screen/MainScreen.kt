package com.example.newstoday.presentation.theme.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newstoday.domain.model.Article
import com.example.newstoday.domain.model.Source
import com.example.newstoday.navigation.NavigationItem
import com.example.newstoday.presentation.theme.account_screen.AccountScreen
import com.example.newstoday.navigation.BottomNavigationBar
import com.example.newstoday.navigation.NavigationObject
import com.example.newstoday.presentation.theme.category_screen_tabBar.CategoryScreen
import com.example.newstoday.presentation.theme.favorite_screen.FavoriteScreen
import com.example.newstoday.presentation.theme.home_screen.DetailsNewsScreen
import com.example.newstoday.presentation.theme.home_screen.HomeScreen
import com.example.newstoday.presentation.theme.personal_account_screen.LanguageScreen
import com.example.newstoday.presentation.theme.personal_account_screen.PersonalAccountScreen
import com.example.newstoday.presentation.theme.test.TestScreen
import com.example.newstoday.presentation.theme.test.TestState
import com.example.newstoday.presentation.theme.test.TestViewModel
import com.example.newstoday.presentation.theme.ui.NewsToDayTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
            }
        },
    )
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Category.route) {
    val viewModel: TestViewModel = hiltViewModel()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle(TestState.Initial)
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen(screenState) {
                navController.currentBackStackEntry?.savedStateHandle?.set("dd", it)
                navController.navigate(NavigationObject.DetailScreen.route)
            }
        }
        composable(NavigationItem.Category.route) {
            CategoryScreen()
        }
        composable(NavigationItem.Favorite.route) {
            FavoriteScreen()
        }
        composable(NavigationItem.Account.route) {
            PersonalAccountScreen{
                navController.navigate("language_screen")
            }
        }
        composable("language_screen"){
            LanguageScreen()
        }
        composable(NavigationObject.DetailScreen.route) {
            val article = navController.previousBackStackEntry?.savedStateHandle?.get<Article>("dd")
                ?: Article(Source("", ""), "", "", "", "", "", "", "")
            DetailsNewsScreen(article)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    NewsToDayTheme {
        MainScreen()
    }
}