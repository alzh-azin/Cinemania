package com.example.cinemania.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemania.core.domain.usecase.GetTrendMedia
import com.example.cinemania.core.network.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendMedia: GetTrendMedia
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        getTrendMediaList(getTrendMedia)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = HomeUiState.Loading
            )

    private fun getTrendMediaList(getTrendMedia: GetTrendMedia): Flow<HomeUiState> {

        val trendMediaList = getTrendMedia.invoke()

        return trendMediaList.map { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    HomeUiState.Success(networkResult.data.orEmpty())
                }

                is NetworkResult.Loading -> {
                    HomeUiState.Loading
                }

                is NetworkResult.Error -> {
                    HomeUiState.Error(errorMessage = networkResult.errorMessage)
                }

                is NetworkResult.Exception -> {
                    HomeUiState.Error(errorMessage = networkResult.exceptionMessage)
                }
            }
        }
    }
}