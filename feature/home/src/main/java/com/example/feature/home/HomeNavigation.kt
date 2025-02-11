package com.example.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarResult
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.core.ui.NavigationRoutes

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(NavigationRoutes.Home, navOptions)

fun NavGraphBuilder.homeScreen(
    onNavigateToDetailsScreen: (Int) -> Unit,
    onNetworkConnectionError: (Boolean) -> Unit,
    snackBarResult: SnackbarResult,
    contentPadding: PaddingValues,
    onTopAppBarAvailable: (Boolean) -> Unit,
    onNavigateBackAvailable: (Boolean) -> Unit,
    onNavigateBarAvailable: (Boolean) -> Unit,
) {
    composable<NavigationRoutes.Home> {
        onTopAppBarAvailable(true)
        onNavigateBackAvailable(false)
        onNavigateBarAvailable(true)

        HomeRoute(
            contentPadding = contentPadding,
            onNavigateToDetailsScreen = onNavigateToDetailsScreen,
            onNetworkConnectionError = onNetworkConnectionError,
            snackBarResult = snackBarResult
        )
    }
}
