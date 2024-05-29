package com.example.cinemania.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.feature.components.ImageSlider

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetailsScreen: () -> Unit,
) {

    val trendMedia by homeViewModel.trendList.collectAsStateWithLifecycle()

    HomeScreen(
        trendMedia,
        onNavigateToDetailsScreen
    )
}

@Composable
fun HomeScreen(
    trendMedia: List<Media>,
    onNavigateToDetailsScreen: () -> Unit,
) {

    if (trendMedia.isNotEmpty())
        ImageSlider(
            trendMedia.map {
                it.posterPath
            },
            onNavigateToDetailsScreen
        )
}