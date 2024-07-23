package com.example.cinemania.feature

import kotlinx.serialization.Serializable

sealed class NavigationRoutes {
    @Serializable
    data object Home : NavigationRoutes()

    @Serializable
    data class Details(
        val id: Int
    ) : NavigationRoutes()

    @Serializable
    data object Search : NavigationRoutes()

    @Serializable
    data object Favorites : NavigationRoutes()
}
