package com.example.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.result.NetworkResult
import com.example.core.domain.model.FilterType
import com.example.core.domain.model.GenreType
import com.example.core.domain.model.GenreType.Companion.DEFAULT_GENRE
import com.example.core.domain.usecase.GetTrendMediaRemote
import com.example.core.domain.usecase.GetMediaByFilterTypeLocal
import com.example.core.domain.usecase.GetTrendMoviesByGenreRemote
import com.example.core.ui.model.toMediaUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMediaByFilterLocal: GetMediaByFilterTypeLocal,
    private val getTrendMediaRemote: GetTrendMediaRemote,
    private val getTrendMoviesByGenreRemote: GetTrendMoviesByGenreRemote
) : ViewModel() {

    private var selectedGenre = MutableStateFlow(DEFAULT_GENRE)

    private val activeRequests = MutableStateFlow<Set<HomeRequestTag>>(emptySet())

    var homeUiState = MutableStateFlow(HomeUiState())
        private set

    var homeUiEffect = MutableSharedFlow<HomeUiEffect>()
        private set

    init {

        getLocalData()

        getRemoteData()

    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.GetData,
            is HomeUiEvent.Refresh -> {
                getRemoteData()
            }

            is HomeUiEvent.SelectGenre -> {
                selectGenreType(event.genre)
            }

            is HomeUiEvent.MediaClicked -> {
                viewModelScope.launch {
                    homeUiEffect.emit(HomeUiEffect.NavigateToDetails(event.mediaId))
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getLocalData() {
        viewModelScope.launch {

            getMediaByFilterLocal.invoke(filterType = FilterType.TrendMedia)
                .collect { trendMediaList ->
                    homeUiState.value =
                        homeUiState.value.copy(trendMedia = trendMediaList.map { media ->
                            media.toMediaUi()
                        })
                }

        }

        viewModelScope.launch {
            selectedGenre.flatMapLatest { genre ->
                getMediaByFilterLocal.invoke(genre, FilterType.TrendMediaByGenre)
            }.collectLatest { trendMediaByGenreList ->
                homeUiState.value =
                    homeUiState.value.copy(trendMediaByGenre = trendMediaByGenreList.map { media ->
                        media.toMediaUi()
                    })
            }

        }

    }

    fun getRemoteData() {

        getTrendMediaList()

        viewModelScope.launch {
            selectedGenre.collect { genre ->
                getTrendMediaByGenre(genre)
            }
        }
    }


    private fun getTrendMediaList() {

        viewModelScope.launch(Dispatchers.IO) {

            val asyncResult = async { getTrendMediaRemote() }

            executeNetworkRequest(asyncResult, HomeRequestTag.GetTrendMedia)

        }
    }

    private fun getTrendMediaByGenre(genre: GenreType) {

        viewModelScope.launch(Dispatchers.IO) {

            val asyncResult = async {
                getTrendMoviesByGenreRemote(
                    genre
                )
            }

            executeNetworkRequest(asyncResult, HomeRequestTag.GetTrendMediaByGenre)
        }
    }

    private fun selectGenreType(genre: GenreType) {
        selectedGenre.value = genre

        homeUiState.value = homeUiState.value.copy(selectedGenre = genre)
    }

    private fun updateLoadingState(requestTag: HomeRequestTag, isLoading: Boolean) {
        viewModelScope.launch {
            activeRequests.value = activeRequests.value.let { currentRequests ->
                if (isLoading) {
                    currentRequests + requestTag
                } else {
                    currentRequests - requestTag
                }
            }
            homeUiState.value =
                homeUiState.value.copy(isLoading = activeRequests.value.isNotEmpty())
        }
    }

    private fun <T> executeNetworkRequest(
        request: Deferred<NetworkResult<T>>,
        requestTag: HomeRequestTag
    ) {

        viewModelScope.launch {

            updateLoadingState(requestTag, true)

            val networkResult = request.await()

            if (networkResult is NetworkResult.Error) {
                homeUiEffect.emit(HomeUiEffect.ShowNetworkError)
            }

            updateLoadingState(requestTag, false)

        }

    }
}