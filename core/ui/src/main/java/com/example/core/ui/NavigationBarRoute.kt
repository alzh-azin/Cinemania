package com.example.core.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.core.designsystem.component.NavigationItem

enum class NavigationBarTypes {
    Home,
    Discover,
    Favorites
}

sealed class NavigationBarRoute(
    val label: NavigationBarTypes,
    val icon: ImageVector,
    val route: NavigationRoutes
) {

    data object Home :
        NavigationBarRoute(
            label = NavigationBarTypes.Home,
            icon = Icons.Filled.Home,
            route = NavigationRoutes.Home
        )

    data object Search :
        NavigationBarRoute(
            label = NavigationBarTypes.Discover,
            icon = Icons.Filled.Search,
            route = NavigationRoutes.Search
        )

    data object Favorites :
        NavigationBarRoute(
            label = NavigationBarTypes.Favorites,
            icon = Icons.Filled.Favorite,
            route = NavigationRoutes.Favorites
        )

    fun toNavigationItem(onClick: (Int) -> Unit) = NavigationItem(
        icon = icon,
        label = label.name,
        onClick = onClick
    )
}