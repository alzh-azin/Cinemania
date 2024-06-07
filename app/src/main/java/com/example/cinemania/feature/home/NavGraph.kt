package com.example.cinemania.feature.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinemania.feature.NavigationRoutes
import com.example.cinemania.feature.details.DetailsRoute

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Home
    ) {

        composable<NavigationRoutes.Home> {
            HomeRoute(
                onNavigateToDetailsScreen = { id ->
                    navController.navigate(NavigationRoutes.Details(id))
                })
        }

        composable<NavigationRoutes.Details> {
            DetailsRoute()
        }
    }
}