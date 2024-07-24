package com.example.cinemania.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemania.core.domain.usecase.GetTrendMediaLocal
import com.example.cinemania.core.domain.usecase.GetTrendMediaRemote
import com.example.cinemania.core.network.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendMediaLocal: GetTrendMediaLocal,
    private val getTrendMediaRemote: GetTrendMediaRemote
) : ViewModel() {


    var homeUiState = MutableStateFlow(HomeUiState())
        private set

    init {

        getData()

        viewModelScope.launch {
            getTrendMediaLocal.invoke().collect { trendMediaList ->
                homeUiState.value = homeUiState.value.copy(trendMedia = trendMediaList)
            }
        }
    }

    fun getData() {
        getTrendMediaList()
    }


    private fun getTrendMediaList() {

        viewModelScope.launch {
            homeUiState.value = homeUiState.value.copy(
                isLoading = true
            )

            when (getTrendMediaRemote.invoke()) {
                is NetworkResult.Success -> {
                    homeUiState.value = homeUiState.value.copy(
                        isLoading = false
                    )
                }

                else -> {}
            }

        }
    }
}