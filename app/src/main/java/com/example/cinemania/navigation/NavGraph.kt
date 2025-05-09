package com.example.cinemania.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.core.ui.NavigationRoutes
import com.example.feature.details.detailsScreen
import com.example.feature.details.navigateToDetails
import com.example.feature.favorites.favoritesScreen
import com.example.feature.home.homeScreen
import com.example.feature.search.searchScreen

@Composable
fun NavGraph(
    contentPadding: PaddingValues,
    snackBarResult: SnackbarResult,
    onTopAppBarAvailable: (Boolean) -> Unit,
    onNavigateBackAvailable: (Boolean) -> Unit,
    onNavigateBarAvailable: (Boolean) -> Unit,
    onNetworkConnectionError: (Boolean) -> Unit,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Home
    ) {
        homeScreen(
            onNavigateToDetailsScreen = { id -> navController.navigateToDetails(id) },
            onNetworkConnectionError = onNetworkConnectionError,
            snackBarResult = snackBarResult,
            contentPadding = contentPadding,
            onTopAppBarAvailable = onTopAppBarAvailable,
            onNavigateBackAvailable = onNavigateBackAvailable,
            onNavigateBarAvailable = onNavigateBarAvailable
        )

        detailsScreen(
            contentPadding = contentPadding,
            onTopAppBarAvailable = onTopAppBarAvailable,
            onNavigateBackAvailable = onNavigateBackAvailable,
            onNavigateBarAvailable = onNavigateBarAvailable
        )

        favoritesScreen(
            contentPadding = contentPadding,
            snackBarResult = snackBarResult,
            onTopAppBarAvailable = onTopAppBarAvailable,
            onNavigateBarAvailable = onNavigateBarAvailable,
        )

        searchScreen(
            contentPadding = contentPadding,
            onNetworkConnectionError = onNetworkConnectionError,
            snackBarResult = snackBarResult,
            onNavigateToDetailsScreen = { navController.navigateToDetails(it) },
            onTopAppBarAvailable = onTopAppBarAvailable,
            onNavigateBarAvailable = onNavigateBarAvailable
        )
    }
}
