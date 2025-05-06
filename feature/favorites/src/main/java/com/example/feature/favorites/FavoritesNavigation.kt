package com.example.feature.favorites

import androidx.annotation.NavigationRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarResult
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.core.ui.NavigationRoutes

fun NavController.navigateToFavorites(navOptions: NavOptions? = null) =
    navigate(NavigationRoutes.Favorites)

fun NavGraphBuilder.favoritesScreen(
    contentPadding: PaddingValues,
    snackBarResult: SnackbarResult,
    onTopAppBarAvailable: (Boolean) -> Unit,
    onNavigateBarAvailable: (Boolean) -> Unit
) {
    composable<NavigationRoutes.Favorites> {
        onTopAppBarAvailable(false)
        onNavigateBarAvailable(true)


    }
}