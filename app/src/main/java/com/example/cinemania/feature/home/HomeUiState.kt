package com.example.cinemania.feature.home

import com.example.cinemania.core.domain.model.Media

data class HomeUiState(
    val trendMedia: List<Media> = emptyList(),
    val isLoading: Boolean = false
)