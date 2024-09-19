package com.example.cinemania.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemania.core.domain.usecase.InsertMedia
import com.example.cinemania.core.domain.usecase.SearchMediaRemote
import com.example.cinemania.core.utils.CinemaniaConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMediaRemote: SearchMediaRemote,
    private val insertMedia: InsertMedia
) : ViewModel() {

    var searchUiState = MutableStateFlow(SearchUiState())
        private set

    var searchUiEffect = MutableSharedFlow<SearchUiEffect>()
        private set

    private var searchJob: Job? = null

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.refresh -> {
                searchMedia(searchUiState.value.searchQuery)
            }

            is SearchUiEvent.onSearchQueryChange -> {

                searchUiState.value =
                    searchUiState.value.copy(searchQuery = event.query, searchResult = null)
                searchJob?.cancel()
                searchJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(1000)
                    searchMedia(event.query)
                }
            }

            is SearchUiEvent.onClearSearch -> {
                searchUiState.value =
                    searchUiState.value.copy(
                        searchQuery = "",
                        searchResult = null,
                        isLoading = false,
                        emptyResult = false
                    )
            }

            is SearchUiEvent.startLoading -> {
                searchUiState.value = searchUiState.value.copy(isLoading = true)
            }

            is SearchUiEvent.stopLoading -> {
                searchUiState.value = searchUiState.value.copy(isLoading = false)
            }

            is SearchUiEvent.onError -> {
                viewModelScope.launch(Dispatchers.IO) {
                    searchUiEffect.emit(SearchUiEffect(showError = true))
                }
            }

            is SearchUiEvent.startPaginationLoading -> {
                searchUiState.value =
                    searchUiState.value.copy(isLoadingNextPage = true, showPaginationError = false)
            }

            is SearchUiEvent.stopPaginationLoading -> {
                searchUiState.value =
                    searchUiState.value.copy(isLoadingNextPage = false, showPaginationError = false)
            }

            is SearchUiEvent.showPaginationError -> {
                searchUiState.value =
                    searchUiState.value.copy(showPaginationError = true, isLoadingNextPage = false)
            }

            is SearchUiEvent.navigateToDetailsScreen -> {
                viewModelScope.launch(Dispatchers.IO) {
                    insertMedia(event.media)
                }
            }
        }
    }

    fun searchMedia(query: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val searchResult = async { searchMediaRemote(query, CinemaniaConstants.PAGE_SIZE) }
            searchUiState.value = searchUiState.value.copy(searchResult = searchResult.await())

        }
    }

}