package com.example.cinemania.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.utils.CinemaniaConstants
import com.example.core.domain.usecase.InsertMedia
import com.example.core.domain.usecase.SearchMediaRemote
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
            is SearchUiEvent.Refresh -> {
                searchMedia(searchUiState.value.searchQuery)
            }

            is SearchUiEvent.ChangeSearchQuery -> {

                searchUiState.value =
                    searchUiState.value.copy(searchQuery = event.query, searchResult = null)
                searchJob?.cancel()
                searchJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(1000)
                    searchMedia(event.query)
                }
            }

            is SearchUiEvent.ClearSearch -> {
                searchUiState.value =
                    searchUiState.value.copy(
                        searchQuery = "",
                        searchResult = null,
                        isLoading = false,
                        emptyResult = false
                    )
            }

            is SearchUiEvent.StartLoading -> {
                searchUiState.value = searchUiState.value.copy(isLoading = true)
            }

            is SearchUiEvent.StopLoading -> {
                searchUiState.value = searchUiState.value.copy(isLoading = false)
            }

            is SearchUiEvent.ShowSearchResultError -> {
                viewModelScope.launch(Dispatchers.IO) {
                    searchUiEffect.emit(SearchUiEffect(showError = true))
                }
            }

            is SearchUiEvent.StartPaginationLoading -> {
                searchUiState.value =
                    searchUiState.value.copy(isLoadingNextPage = true, showPaginationError = false)
            }

            is SearchUiEvent.StopPaginationLoading -> {
                searchUiState.value =
                    searchUiState.value.copy(isLoadingNextPage = false, showPaginationError = false)
            }

            is SearchUiEvent.ShowPaginationError -> {
                searchUiState.value =
                    searchUiState.value.copy(showPaginationError = true, isLoadingNextPage = false)
            }

            is SearchUiEvent.NavigateToDetailsScreen -> {
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