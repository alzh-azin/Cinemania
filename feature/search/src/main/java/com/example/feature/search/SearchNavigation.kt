package com.example.feature.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarResult
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

fun NavController.navigateToSearch(navOptions: NavOptions? = null) =
    navigate(SearchRoute, navOptions)

fun NavGraphBuilder.searchScreen(
    contentPadding: PaddingValues,
    onNetworkConnectionError: (Boolean) -> Unit,
    snackBarResult: SnackbarResult,
    onNavigateToDetailsScreen: (Int) -> Unit,
    onTopAppBarAvailable: (Boolean) -> Unit,
    onNavigateBarAvailable: (Boolean) -> Unit
) {
    composable<SearchRoute> {
        onTopAppBarAvailable(false)
        onNavigateBarAvailable(false)

        SearchRoute(
            contentPadding = contentPadding,
            onNetworkConnectionError = onNetworkConnectionError,
            snackBarResult = snackBarResult,
            onNavigateToDetailsScreen = onNavigateToDetailsScreen
        )
    }
}
