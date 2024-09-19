package com.example.cinemania.feature.search

import com.example.cinemania.core.domain.model.Media

sealed class SearchUiEvent {

    data object refresh : SearchUiEvent()

    data class onSearchQueryChange(val query: String) : SearchUiEvent()

    data object onClearSearch : SearchUiEvent()

    data object startLoading : SearchUiEvent()

    data object stopLoading : SearchUiEvent()

    data object startPaginationLoading : SearchUiEvent()

    data object stopPaginationLoading : SearchUiEvent()

    data object showPaginationError : SearchUiEvent()

    data object onError : SearchUiEvent()

    data class navigateToDetailsScreen(val media: Media) : SearchUiEvent()
}