package com.example.newstoday.presentation.theme.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newstoday.domain.model.Article
import com.example.newstoday.domain.model.Source
import com.example.newstoday.navigation.BottomNavigationBar
import com.example.newstoday.navigation.NavigationItem
import com.example.newstoday.navigation.NavigationObject
import com.example.newstoday.presentation.theme.category_screen_tabBar.CategoryScreen
import com.example.newstoday.presentation.theme.favorite_screen.FavoriteScreen
import com.example.newstoday.presentation.theme.home_screen.DetailsNewsScreen
import com.example.newstoday.presentation.theme.home_screen.HomeScreen
import com.example.newstoday.presentation.theme.login_screen.LoginScreen
import com.example.newstoday.presentation.theme.login_screen.Register
import com.example.newstoday.presentation.theme.onboarding_screen.OnboardingScreen
import com.example.newstoday.presentation.theme.personal_account_screen.LanguageScreen
import com.example.newstoday.presentation.theme.personal_account_screen.PersonalAccountScreen
import com.example.newstoday.presentation.theme.ui.NewsToDayTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        "home" -> true
        "category" -> true
        "favorite" -> true
        "account" -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
             if (showBottomBar) BottomNavigationBar(navController)
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
            }
        },
    )
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationObject.LoginScreen.route) {
        composable(NavigationObject.LoginScreen.route) {

            LoginScreen(
                navigateToHome = { navController.navigate(NavigationItem.Home.route) }) {
                navController.navigate("onboarding_screen")
            }
        }
        composable(NavigationItem.Home.route) {
            HomeScreen {
                navController.currentBackStackEntry?.savedStateHandle?.set("dd", it)
                navController.navigate(NavigationObject.DetailScreen.route)
            }
        }
        composable(NavigationItem.Category.route) {
            CategoryScreen()
        }
        composable(NavigationItem.Favorite.route) {
            FavoriteScreen {
                navController.currentBackStackEntry?.savedStateHandle?.set("dd", it)
                navController.navigate(NavigationObject.DetailScreen.route)
            }
        }
        composable("onboarding_screen"){
            OnboardingScreen {
                navController.navigate(NavigationItem.Category.route)
            }
        }
        composable(NavigationItem.Account.route) {
            PersonalAccountScreen(
                navigateToLanguageScreen = {navController.navigate("language_screen")} ) {
                navController.navigate(NavigationObject.LoginScreen.route)
            }
        }
        composable("language_screen") {
            LanguageScreen()
        }
        composable(NavigationObject.DetailScreen.route) {
            val article =
                navController.previousBackStackEntry?.savedStateHandle?.get<Article>("dd")
                    ?: Article(Source("", ""), "", "", "", "", "", "", "")
            DetailsNewsScreen(article){
                navController.navigate(NavigationItem.Home.route)
            }
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