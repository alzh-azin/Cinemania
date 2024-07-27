package com.example.cinemania.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinemania.feature.components.ImageSlider
import com.example.cinemania.feature.components.PullToRefreshContent

@Composable
fun HomeRoute(
    contentPadding: PaddingValues,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNetworkConnectionError: (isConnected: Boolean) -> Unit,
    snackBarResult: SnackbarResult,
    onSnackBarRetryClick: () -> Unit,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
) {
    val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()
    val homeUiEffect by homeViewModel.homeUiEffect.collectAsStateWithLifecycle(HomeUiEffect())

    LaunchedEffect(key1 = snackBarResult) {
        if (snackBarResult == SnackbarResult.ActionPerformed) {
            homeViewModel.getData()
            onSnackBarRetryClick()
        }
    }

    HomeScreen(
        contentPadding = contentPadding,
        homeUiState = homeUiState,
        homeUiEffect = homeUiEffect,
        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
        onRefresh = { homeViewModel.getData() },
        onNetworkConnectionError = onNetworkConnectionError,
    )
}

@Composable
fun HomeScreen(
    contentPadding: PaddingValues,
    homeUiState: HomeUiState,
    homeUiEffect: HomeUiEffect,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    onRefresh: () -> Unit,
    onNetworkConnectionError: (isConnected: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    if (homeUiEffect.showError) {
        onNetworkConnectionError(false)
    }

    PullToRefreshContent(
        isRefreshing = homeUiState.isLoading,
        contentPadding = contentPadding,
        onRefresh = { onRefresh() },
    ) {
        if (homeUiState.trendMedia.isNotEmpty()) {
            Row {
                ImageSlider(
                    homeUiState.trendMedia,
                    onNavigateToDetailsScreen
                )
            }
        }
    }
}
