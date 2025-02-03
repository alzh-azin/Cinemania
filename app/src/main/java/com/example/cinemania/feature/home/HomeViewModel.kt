package com.example.cinemania.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.result.NetworkResult
import com.example.core.domain.usecase.GetTrendMediaLocal
import com.example.core.domain.usecase.GetTrendMediaRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendMediaLocal: GetTrendMediaLocal,
    private val getTrendMediaRemote: GetTrendMediaRemote
) : ViewModel() {

    private var selectedGenre = 0

    var homeUiState = MutableStateFlow(HomeUiState())
        private set

    var homeUiEffect = MutableSharedFlow<HomeUiEffect>()
        private set

    init {

        viewModelScope.launch {
            getTrendMediaLocal.invoke().collect { trendMediaList ->
                homeUiState.value = homeUiState.value.copy(trendMedia = trendMediaList)
            }
        }

        getData()

    }

    fun getData() {
        getTrendMediaList()
    }


    private fun getTrendMediaList() {

        viewModelScope.launch(Dispatchers.IO) {

            val asyncResult = async { getTrendMediaRemote() }

            homeUiState.value = homeUiState.value.copy(
                isLoading = true
            )

            when (asyncResult.await()) {
                is NetworkResult.Success -> {
                    homeUiState.value = homeUiState.value.copy(
                        isLoading = false
                    )
                }

                is NetworkResult.Error -> {
                    homeUiEffect.emit(HomeUiEffect(showError = true))
                    homeUiState.value = homeUiState.value.copy(
                        isLoading = false
                    )
                }
            }

        }
    }

    fun selectGenreType(index: Int) {
        val genreList = homeUiState.value.genreList.toMutableList()

        genreList[selectedGenre] = genreList[selectedGenre].copy(isSelected = false)
        genreList[index] = genreList[index].copy(isSelected = true)

        selectedGenre = index
        homeUiState.value = homeUiState.value.copy(genreList = genreList)
    }
}