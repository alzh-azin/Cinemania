package com.example.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

object CinemaniaNavigationDefaults {

    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.surfaceContainerLow

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}