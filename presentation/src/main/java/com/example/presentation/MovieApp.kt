package com.example.presentation


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.AllMoviesPage.BrowsePageScreen
import com.example.presentation.AllMoviesPage.BrowsePageViewModel
import com.example.presentation.FavoritesPage.FavoritePageScreen
import com.example.presentation.HomePage.HomePageScreen
import com.example.presentation.ProfileScreen.ProfilePageScreen
import com.example.presentation.navbar.BottomNavBar
import com.example.presentation.navigation.Screen


@Composable
fun MovieApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) { HomePageScreen() }
        composable(route = Screen.Browse.route) {

            BrowsePageScreen()
        }
        composable(route = Screen.Favorite.route) { FavoritePageScreen() }
        composable(route = Screen.Profile.route) { ProfilePageScreen() }
    }

    BottomNavBar(navController = navController)
}
