package com.example.cinemania.feature

import kotlinx.serialization.Serializable

sealed class NavigationRoutes {
    @Serializable
    object Home

    @Serializable
    object Details
}
