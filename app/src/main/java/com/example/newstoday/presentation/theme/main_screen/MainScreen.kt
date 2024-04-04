package com.example.newstoday.presentation.theme.main_screen

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.newstoday.presentation.theme.auth.GoogleAuthUIClient
import com.example.newstoday.presentation.theme.auth.SignInViewModel
import com.example.newstoday.presentation.theme.Recommended_Screen.RecScreen
import com.example.newstoday.presentation.theme.category_screen_first_entry.CategoryScreenFirstEntry
import com.example.newstoday.presentation.theme.category_screen_tabBar.CategoryScreen
import com.example.newstoday.presentation.theme.detail_news_screen.DetailsNewsScreen
import com.example.newstoday.presentation.theme.favorite_screen.FavoriteScreen
import com.example.newstoday.presentation.theme.home_screen.HomeScreen
import com.example.newstoday.presentation.theme.login_screen.LoginScreen
import com.example.newstoday.presentation.theme.login_screen.LoginScreenViewModel
import com.example.newstoday.presentation.theme.onboarding_screen.OnboardingScreen
import com.example.newstoday.presentation.theme.personal_account_screen.LanguageScreen
import com.example.newstoday.presentation.theme.personal_account_screen.PersonalAccountScreen
import kotlinx.coroutines.launch

@Composable
fun MainScreen(googleAuthUIClient: GoogleAuthUIClient) {
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
                Navigation(
                    googleAuthUIClient = googleAuthUIClient,
                    navController = navController
                )
            }
        },
    )
}

@Composable
fun Navigation(googleAuthUIClient: GoogleAuthUIClient, navController: NavHostController) {
    val scope = rememberCoroutineScope()

    NavHost(navController, startDestination = NavigationObject.LoginScreen.route) {
        composable(NavigationObject.LoginScreen.route) {
            val loginViewModel = hiltViewModel<LoginScreenViewModel>()
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                val user = googleAuthUIClient.getSignedInUser()
                if (user != null) {
                    user.userName?.let { it1 -> loginViewModel.saveIsLoginStatus("${it1}@gmail.com") }
                    navController.navigate(NavigationItem.Home.route)
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        scope.launch {
                            val signInResult = googleAuthUIClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    val user = googleAuthUIClient.getSignedInUser()
                    if (user != null) {
                        Toast.makeText(
                            navController.context,
                            "Sign in successful",
                            Toast.LENGTH_LONG
                        ).show()
                        user.userName?.let { userName ->
                            loginViewModel.saveUser(userName, "${userName}@gmail.com", "123")
                            loginViewModel.saveIsLoginStatus("${userName}@gmail.com")

                            if (loginViewModel.checkGoogleUser(userName)) {
                                navController.navigate(NavigationItem.Home.route)
                            } else {
                                navController.navigate(NavigationObject.OnboardingScreen.route)
                            }
                            viewModel.resetState()
                        }
                    }
                }
            }

            LoginScreen(
                state = state,
                onSignInClick = {
                    scope.launch {
                        val signInIntentSender = googleAuthUIClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                },
                navigateToHomeScreen = { navController.navigate(NavigationItem.Home.route) }) {
                navController.navigate(NavigationObject.OnboardingScreen.route)
            }
        }

        composable(NavigationObject.OnboardingScreen.route) {
            OnboardingScreen {
                navController.navigate(NavigationObject.CategoryScreenFirstEntry.route)
            }
        }

        composable(NavigationObject.CategoryScreenFirstEntry.route) {
            CategoryScreenFirstEntry {
                navController.navigate(NavigationItem.Home.route)
            }
        }

        composable(NavigationItem.Home.route) {
            HomeScreen(navigateToDetail = {
                navController .currentBackStackEntry?.savedStateHandle?.set("dd", it)
                navController.navigate(NavigationObject.DetailScreen.route)
            },
                navigateToDetail2 = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("qq", it)
                    navController.navigate(NavigationObject.RecommendedScreen.route)
                }
            )
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

        composable(NavigationItem.Account.route) {
            PersonalAccountScreen(
                navigateToLanguageScreen = {navController.navigate("language_screen")},
                userData = googleAuthUIClient.getSignedInUser(),
                onSignOutClick = {
                    scope.launch {
                        googleAuthUIClient.signOut()
                        Toast.makeText(
                            navController.context,
                            "Sign out",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.navigate(NavigationObject.LoginScreen.route)
                    }
                }) {
            }
        }

        composable("language_screen") {
            LanguageScreen()
        }

        composable(NavigationObject.DetailScreen.route) {
            val article =
                navController.previousBackStackEntry?.savedStateHandle?.get<Article>("dd")
                    ?: Article(Source("", ""), "", "", "", "", "", "", "")
            DetailsNewsScreen(article) {
                navController.popBackStack()
            }
        }
        composable(NavigationObject.RecommendedScreen.route) {
            val articles =
                navController.previousBackStackEntry?.savedStateHandle?.get<List<Article>>("qq")
            if (articles != null) {
                RecScreen(articles){
                    navController.currentBackStackEntry?.savedStateHandle?.set("dd", it)
                    navController.navigate(NavigationObject.DetailScreen.route)

                }
            }

        }

    }
}