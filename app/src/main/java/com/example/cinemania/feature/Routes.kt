package com.example.cinemania.feature

import kotlinx.serialization.Serializable

sealed class NavigationRoutes {
    @Serializable
    object Home

    @Serializable
    data class Details(
        val id: Int
    )
}
