package com.example.presentation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.presentation.AllMoviesPage.BrowsePageScreen
import com.example.presentation.FavoritesPage.FavoritePageScreen
import com.example.presentation.HomePage.HomePageScreen
import com.example.presentation.MovieDetailsPage.MovieDetailsScreen
import com.example.presentation.ProfileScreen.Login.LoginPageScreen
import com.example.presentation.ProfileScreen.ProfileSetting.Profile
import com.example.presentation.ProfileScreen.ProfileSetting.UpdateEmail.UpdateEmailScreen
import com.example.presentation.ProfileScreen.Register.ProfilePageScreen
import com.example.presentation.navbar.BottomNavBar
import com.example.presentation.navigation.Screen


@Composable
fun MovieApp() {
    val navController = rememberNavController()

    val isLoggedIn = remember { mutableStateOf(false) }



    val startDestination = if (isLoggedIn.value) Screen.Home.route else Screen.Login.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isLoggedIn.value) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = Screen.Login.route) { LoginPageScreen(onLoginSuccess = {
                isLoggedIn.value = true
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            },
                navController = navController,
                onNavigateToRegister = {navController.navigate(Screen.Register.route)}
                ) }

            composable(route = Screen.Register.route) { ProfilePageScreen(
                onNavigateToRegister = { navController.navigate(Screen.Login.route) },
                onRegisterSuccessful = {navController.navigate(Screen.Login.route)}
            ) }

            composable(route = Screen.Home.route) { HomePageScreen(navController = navController) }

            composable(route = Screen.Browse.route) {
                BrowsePageScreen(navController = navController)
            }
            composable(
                route = "movieDetails/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
                MovieDetailsScreen(
                    movieId,
                    navController = navController,
                )
            }
            composable(route = Screen.Favorite.route) { FavoritePageScreen() }

            composable(route = Screen.Profile.route) {
                Profile(navController = navController, onNavigateToSetting = {navController.navigate(Screen.UpdateEmail.route)})
            }

            composable(route = Screen.UpdateEmail.route) {
                UpdateEmailScreen(navController = navController)
            }

        }
    }
}

