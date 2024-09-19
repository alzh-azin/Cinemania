package com.example.cinemania.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cinemania.feature.NavigationRoutes
import com.example.cinemania.feature.details.DetailsRoute
import com.example.cinemania.feature.search.SearchRoute

@Composable
fun NavGraph(
    contentPadding: PaddingValues,
    snackBarResult: SnackbarResult,
    onTopAppBarAvailable: (Boolean) -> Unit,
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

            onTopAppBarAvailable(true)
            onNavigateBackAvailable(false)
            onNavigateBarAvailable(true)

            HomeRoute(
                contentPadding = contentPadding,
                onNavigateToDetailsScreen = { id ->
                    navController.navigate(NavigationRoutes.Details(id))
                },
                onNetworkConnectionError = onNetworkConnectionError,
                snackBarResult = snackBarResult
            )
        }

        composable<NavigationRoutes.Details> {

            onTopAppBarAvailable(true)
            onNavigateBackAvailable(true)
            onNavigateBarAvailable(false)

            DetailsRoute(contentPadding = contentPadding)
        }

        composable<NavigationRoutes.Discover> {

            onTopAppBarAvailable(true)
            onNavigateBarAvailable(true)
        }

        composable<NavigationRoutes.Favorites> {

            onTopAppBarAvailable(true)
            onNavigateBarAvailable(true)
        }

        composable<NavigationRoutes.Search> {

            onTopAppBarAvailable(false)
            onNavigateBarAvailable(false)

            SearchRoute(
                contentPadding = contentPadding,
                onNetworkConnectionError = onNetworkConnectionError,
                snackBarResult = snackBarResult,
                onNavigateToDetailsScreen = {
                    navController.navigate(NavigationRoutes.Details(it))
                }
            )
        }
    }
}