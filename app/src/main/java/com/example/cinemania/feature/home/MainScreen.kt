package com.example.cinemania.feature.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinemania.feature.NavigationRoutes

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController = navController, startDestination = NavigationRoutes.Home) {

        composable<NavigationRoutes.Home> {
            HomeRoute(navController = navController)
        }

        composable<NavigationRoutes.Details> {
            Text(text = "This is the details screen")
        }
    }
}