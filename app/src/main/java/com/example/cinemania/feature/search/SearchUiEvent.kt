package com.example.cinemania.feature.search

import com.example.core.domain.model.Media

sealed class SearchUiEvent {

    data object Refresh : SearchUiEvent()

    data class ChangeSearchQuery(val query: String) : SearchUiEvent()

    data object ClearSearch : SearchUiEvent()

    data object StartLoading : SearchUiEvent()

    data object StopLoading : SearchUiEvent()

    data object StartPaginationLoading : SearchUiEvent()

    data object StopPaginationLoading : SearchUiEvent()

    data object ShowPaginationError : SearchUiEvent()

    data object ShowSearchResultError : SearchUiEvent()

    data class NavigateToDetailsScreen(val media: Media) : SearchUiEvent()
}