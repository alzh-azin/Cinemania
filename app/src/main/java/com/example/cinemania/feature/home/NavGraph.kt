package com.example.cinemania.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cinemania.feature.NavigationRoutes
import com.example.cinemania.feature.details.DetailsRoute

@Composable
fun NavGraph(
    contentPadding: PaddingValues,
    snackBarResult: SnackbarResult,
    onSnackBarRetryClick: () -> Unit,
    onNavigateBackAvailable: (Boolean) -> Unit,
    onNavigateBarAvailable: (Boolean) -> Unit,
    onNetworkConnectionError: (isConnected: Boolean) -> Unit,
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Home
    ) {

        composable<NavigationRoutes.Home> {

            onNavigateBackAvailable(false)
            onNavigateBarAvailable(true)
            HomeRoute(
                contentPadding = contentPadding,
                onNavigateToDetailsScreen = { id ->
                    navController.navigate(NavigationRoutes.Details(id))
                },
                onNetworkConnectionError = onNetworkConnectionError,
                onSnackBarRetryClick = onSnackBarRetryClick,
                snackBarResult = snackBarResult
            )
        }

        composable<NavigationRoutes.Details> {
            onNavigateBackAvailable(true)
            onNavigateBarAvailable(false)
            DetailsRoute(contentPadding = contentPadding)
        }

        composable<NavigationRoutes.Search> {
            onNavigateBarAvailable(true)
        }

        composable<NavigationRoutes.Favorites> {
            onNavigateBarAvailable(true)
        }
    }
}