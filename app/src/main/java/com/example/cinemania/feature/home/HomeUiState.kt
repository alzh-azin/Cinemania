package com.example.cinemania.feature.home

import com.example.cinemania.core.domain.model.Media

sealed interface HomeUiState {
    data class Success(val trendMedia: List<Media>) : HomeUiState
    data class Error(val errorMessage: String) : HomeUiState
    data object Loading : HomeUiState
}