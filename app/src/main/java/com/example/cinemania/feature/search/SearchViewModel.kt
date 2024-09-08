package com.example.cinemania.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemania.core.domain.usecase.SearchMediaRemote
import com.example.cinemania.core.network.utils.NetworkResult
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
    private val searchMediaRemote: SearchMediaRemote
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

                searchUiState.value = searchUiState.value.copy(searchQuery = event.query)

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
                        searchResult = emptyList(),
                        isLoading = false,
                        emptyResult = false
                    )
            }
        }
    }

    fun searchMedia(query: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val asyncResult = async { searchMediaRemote(query) }

            searchUiState.value = searchUiState.value.copy(isLoading = true)

            when (val result = asyncResult.await()) {
                is NetworkResult.Success -> {

                    searchUiState.value = searchUiState.value
                        .copy(
                            searchResult = result.data.orEmpty(),
                            emptyResult = if (result.data?.isEmpty() == true)
                                true
                            else
                                false
                        )

                    searchUiState.value = searchUiState.value.copy(isLoading = false)

                }

                is NetworkResult.Error -> {

                    searchUiEffect.emit(SearchUiEffect(showError = true))
                    searchUiState.value = searchUiState.value.copy(isLoading = false)

                }
            }

        }
    }

}