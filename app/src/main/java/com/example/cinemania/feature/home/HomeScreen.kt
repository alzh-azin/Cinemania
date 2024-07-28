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
    onNetworkConnectionError: (showError: Boolean) -> Unit,
    snackBarResult: SnackbarResult,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
) {
    val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        homeViewModel.homeUiEffect.collect {
            onNetworkConnectionError(true)
        }
    }

    HomeScreen(
        contentPadding = contentPadding,
        homeUiState = homeUiState,
        snackBarResult = snackBarResult,
        onRefresh = { homeViewModel.getData() },
        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
        onNetworkConnectionError = onNetworkConnectionError,
    )
}

@Composable
fun HomeScreen(
    contentPadding: PaddingValues,
    homeUiState: HomeUiState,
    snackBarResult: SnackbarResult,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    onRefresh: () -> Unit,
    onNetworkConnectionError: (showError: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(key1 = snackBarResult) {
        if (snackBarResult == SnackbarResult.ActionPerformed) {
            onRefresh()
            onNetworkConnectionError(false)
        }
    }

    if (homeUiState.isLoading)
        onNetworkConnectionError(false)

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
