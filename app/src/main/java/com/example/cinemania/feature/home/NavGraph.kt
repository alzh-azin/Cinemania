package com.example.cinemania.feature.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cinemania.feature.NavigationRoutes
import com.example.cinemania.feature.details.DetailsRoute

@Composable
fun NavGraph(
    onNavigateBackAvailable: (Boolean) -> Unit,
    navController: NavHostController,
) {


    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Home
    ) {

        composable<NavigationRoutes.Home> {

            onNavigateBackAvailable(false)
            HomeRoute(
                onNavigateToDetailsScreen = { id ->
                    navController.navigate(NavigationRoutes.Details(id))
                })
        }

        composable<NavigationRoutes.Details> {
            onNavigateBackAvailable(true)
            DetailsRoute()
        }
    }
}