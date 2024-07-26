package com.example.cinemania.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
    onNavigateToDetailsScreen: (id: Int) -> Unit,
) {
    val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        contentPadding = contentPadding,
        homeUiState = homeUiState,
        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
        onRefresh = { homeViewModel.getData() }
    )
}

@Composable
fun HomeScreen(
    contentPadding: PaddingValues,
    homeUiState: HomeUiState,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {

    PullToRefreshContent(
        isRefreshing = homeUiState.isLoading,
        onRefresh = { onRefresh.invoke() },
        modifier = modifier.padding(contentPadding),
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
