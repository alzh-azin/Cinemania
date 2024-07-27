package com.example.cinemania.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemania.core.domain.usecase.GetTrendMediaLocal
import com.example.cinemania.core.domain.usecase.GetTrendMediaRemote
import com.example.cinemania.core.network.utils.NetworkResult
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

                else -> {
                    homeUiEffect.emit(HomeUiEffect(showError = true))
                    homeUiState.value = homeUiState.value.copy(
                        isLoading = false
                    )
                }
            }

        }
    }
}