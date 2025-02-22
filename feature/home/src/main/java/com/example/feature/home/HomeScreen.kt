package com.example.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.core.common.utils.CinemaniaConstants.BASE_IMAGE_URL
import com.example.core.common.utils.CinemaniaConstants.BASE_IMAGE_URL_LOW_QUALITY
import com.example.feature.component.ImageSlider
import com.example.feature.component.PullToRefreshContent
import com.example.core.domain.model.GenreType
import com.example.core.domain.model.Media

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
        onRefresh = { homeViewModel.getRemoteData() },
        onSelectGenre = { genre ->
            homeViewModel.selectGenreType(genre)
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
    onSelectGenre: (genre: GenreType) -> Unit,
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
                    selectedGenre = homeUiState.selectedGenre,
                    onSelectGenre = onSelectGenre
                )
                TrendMediaByGenreList(
                    mediaList = homeUiState.trendMediaByGenre,
                    selectedGenre = homeUiState.selectedGenre,
                    onNavigateToDetailsScreen = onNavigateToDetailsScreen
                )
            }
        }
    }
}

@Composable
fun GenreList(
    genreList: List<GenreType>,
    selectedGenre: GenreType,
    onSelectGenre: (genre: GenreType) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
    ) {
        items(genreList) { item ->
            GenreChip(
                title = item.genreName,
                isSelected = item == selectedGenre,
                onSelectGenre = { onSelectGenre(item) }
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

@Composable
fun TrendMediaByGenreList(
    mediaList: List<Media>,
    selectedGenre: GenreType,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    var previousGenre by rememberSaveable { mutableStateOf(selectedGenre) }

    LaunchedEffect(selectedGenre) {
        if (previousGenre != selectedGenre) {
            listState.scrollToItem(0)
            previousGenre = selectedGenre
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        items(mediaList) { media ->
            TrendMediaByGenreItem(media, onNavigateToDetailsScreen)
        }
    }
}

@Composable
fun TrendMediaByGenreItem(
    media: Media,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val painter =
        rememberAsyncImagePainter(
            model =
            "$BASE_IMAGE_URL$BASE_IMAGE_URL_LOW_QUALITY${media.posterPath}"
        )

    Column(
        modifier
            .width(150.dp)
            .wrapContentHeight()
            .padding(4.dp)
            .clickable {
                onNavigateToDetailsScreen(media.id)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = modifier.clip(shape = RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(6 / 8f),
                contentScale = ContentScale.FillBounds,
            )
        }


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            text = media.title.orEmpty(),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
    }

}