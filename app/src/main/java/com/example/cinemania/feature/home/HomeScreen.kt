package com.example.cinemania.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FilterChip
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinemania.core.domain.model.Genre
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
        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
        onNetworkConnectionError = onNetworkConnectionError,
        onRefresh = { homeViewModel.getData() },
        onSelectGenre = { index ->
            homeViewModel.selectGenreType(index)
        }
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
    onSelectGenre: (index: Int) -> Unit,
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
            Column {
                Row {
                    ImageSlider(
                        homeUiState.trendMedia,
                        onNavigateToDetailsScreen
                    )
                }
                GenreList(
                    genreList = homeUiState.genreList,
                    onSelectGenre = onSelectGenre
                )
            }
        }
    }
}

@Composable
fun GenreList(
    genreList: List<Genre>,
    modifier: Modifier = Modifier,
    onSelectGenre: (index: Int) -> Unit,
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
    ) {
        itemsIndexed(genreList) { index, item ->
            GenreChip(
                title = item.genreName,
                isSelected = item.isSelected,
                onSelectGenre = { onSelectGenre(index) }
            )
        }
    }
}

@Composable
fun GenreChip(
    title: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onSelectGenre: () -> Unit,
) {
    FilterChip(
        modifier = modifier.padding(4.dp),
        onClick = {
            onSelectGenre()
        },
        label = {
            Text(text = title)
        },
        selected = isSelected
    )
}