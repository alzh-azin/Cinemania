package com.example.feature.details

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class DetailsRoute(val id: Int)

fun NavController.navigateToDetails(id: Int, navOptions: NavOptions? = null) =
    navigate(DetailsRoute(id), navOptions)

fun NavGraphBuilder.detailsScreen(
    contentPadding: PaddingValues,
    onTopAppBarAvailable: (Boolean) -> Unit,
    onNavigateBackAvailable: (Boolean) -> Unit,
    onNavigateBarAvailable: (Boolean) -> Unit
) {
    composable<DetailsRoute> {

        onTopAppBarAvailable(true)
        onNavigateBackAvailable(true)
        onNavigateBarAvailable(false)

        DetailsRoute(contentPadding = contentPadding)
    }
}
