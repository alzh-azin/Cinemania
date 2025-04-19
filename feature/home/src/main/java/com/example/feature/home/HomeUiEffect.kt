package com.example.feature.home

sealed class HomeUiEffect {
    data object ShowNetworkError : HomeUiEffect()
    data class NavigateToDetails(val mediaId : Int) : HomeUiEffect()
}