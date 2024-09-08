package com.example.cinemania.feature.search

sealed class SearchUiEvent {

    data object refresh : SearchUiEvent()

    data class onSearchQueryChange(val query: String) : SearchUiEvent()

    data object onClearSearch : SearchUiEvent()
}