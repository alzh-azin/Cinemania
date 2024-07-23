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

@Composable
fun HomeRoute(
    contentPadding: PaddingValues,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetailsScreen: (id: Int) -> Unit,
) {
    val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        contentPadding,
        homeUiState,
        onNavigateToDetailsScreen
    )
}

@Composable
fun HomeScreen(
    contentPadding: PaddingValues,
    homeUiState: HomeUiState,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    when (homeUiState) {
        is HomeUiState.Loading -> {
        }

        is HomeUiState.Success -> {
            if (homeUiState.trendMedia.isNotEmpty()) {
                Row(modifier = modifier.padding(contentPadding)) {
                    ImageSlider(
                        homeUiState.trendMedia,
                        onNavigateToDetailsScreen
                    )
                }
            }
        }

        is HomeUiState.Error -> {

        }
    }

}