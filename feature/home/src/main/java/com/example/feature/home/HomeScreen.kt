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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.cinemania.core.designSystem.R
import com.example.core.common.utils.CinemaniaConstants.BASE_IMAGE_URL
import com.example.core.common.utils.CinemaniaConstants.BASE_IMAGE_URL_LOW_QUALITY
import com.example.core.designsystem.component.ImageSlider
import com.example.core.designsystem.component.SliderItem
import com.example.core.designsystem.preview.PreviewContainer
import com.example.core.designsystem.component.PullToRefreshContent
import com.example.core.domain.model.GenreType
import com.example.core.ui.model.MediaUi
import kotlinx.coroutines.flow.collectLatest

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
        homeViewModel.homeUiEffect.collectLatest { effect ->
            when (effect) {
                is HomeUiEffect.NavigateToDetails -> {
                    onNavigateToDetailsScreen(effect.mediaId)
                }

                is HomeUiEffect.ShowNetworkError -> onNetworkConnectionError(true)
            }

        }
    }

    LaunchedEffect(key1 = snackBarResult) {
        if (snackBarResult == SnackbarResult.ActionPerformed) {
            homeViewModel.getRemoteData()
            onNetworkConnectionError(false)
        }
    }

    LaunchedEffect(homeUiState.isLoading) {
        if (homeUiState.isLoading) {
            onNetworkConnectionError(false)
        }
    }

    HomeScreen(
        contentPadding = contentPadding,
        homeUiState = homeUiState,
        onEvent = { event ->
            homeViewModel.onEvent(event)
        }
    )
}

@Composable
fun HomeScreen(
    contentPadding: PaddingValues,
    homeUiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    PullToRefreshContent(
        isRefreshing = homeUiState.isLoading,
        contentPadding = contentPadding,
        onRefresh = { onEvent(HomeUiEvent.GetData) },
        modifier = modifier
    ) {
        if (homeUiState.trendMedia.isNotEmpty()) {
            Column {
                Row {
                    ImageSlider(
                        images = homeUiState.trendMedia.map {
                            //TODO implement a mapper function after MediaUi is implemented
                            SliderItem(
                                id = it.id,
                                imageUrl = it.posterPath.orEmpty()
                            )
                        },
                        onItemClick = { mediaId ->
                            onEvent(HomeUiEvent.MediaClicked(mediaId))
                        }
                    )
                }
                GenreList(
                    genreList = homeUiState.genreList,
                    selectedGenre = homeUiState.selectedGenre,
                    onEvent = onEvent
                )
                TrendMediaByGenreList(
                    mediaList = homeUiState.trendMediaByGenre,
                    selectedGenre = homeUiState.selectedGenre,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Composable
fun GenreList(
    genreList: List<GenreType>,
    selectedGenre: GenreType,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
    ) {
        items(genreList) { item ->
            GenreChip(
                genre = item,
                isSelected = item == selectedGenre,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun GenreChip(
    genre: GenreType,
    isSelected: Boolean,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    FilterChip(
        modifier = modifier.padding(4.dp),
        onClick = {
            onEvent(HomeUiEvent.SelectGenre(genre))
        },
        label = {
            Text(text = genre.genreName)
        },
        selected = isSelected
    )
}

@Composable
fun TrendMediaByGenreList(
    mediaList: List<MediaUi>,
    selectedGenre: GenreType,
    onEvent: (HomeUiEvent) -> Unit,
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
            TrendMediaByGenreItem(media, onEvent)
        }
    }
}

@Composable
fun TrendMediaByGenreItem(
    media: MediaUi,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val painter = if (!LocalInspectionMode.current) {
        rememberAsyncImagePainter(
            model =
            "$BASE_IMAGE_URL$BASE_IMAGE_URL_LOW_QUALITY${media.posterPath}"
        )
    } else {
        painterResource(R.drawable.ic_preview_placeholder)
    }

    Column(
        modifier
            .width(150.dp)
            .wrapContentHeight()
            .padding(4.dp)
            .clickable {
                onEvent(HomeUiEvent.MediaClicked(media.id))
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6 / 8f),
                contentScale = ContentScale.FillBounds,
            )
        }


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            text = media.title.orEmpty(),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(name = "HomeScreen", showBackground = true)
@Composable
fun HomeScreenPreview(
    @PreviewParameter(HomeUiStatePreviewProvider::class) state: HomeUiState
) {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        PreviewContainer {
            HomeScreen(
                contentPadding = PaddingValues(0.dp),
                homeUiState = state,
                onEvent = {}
            )
        }
    }
}

@Preview("TrendMediaByGenreList", showBackground = true)
@Composable
fun TrendMediaByGenreListPreview(
    @PreviewParameter(MediaListPreviewProvider::class) list: List<MediaUi>
) {
    CompositionLocalProvider(
        LocalInspectionMode provides true,
    ) {
        PreviewContainer {
            TrendMediaByGenreList(
                mediaList = list,
                GenreType.Drama,
                onEvent = {}
            )
        }

    }

}

@Preview("TrendMediaByGenreItem", showBackground = true)
@Composable
fun TrendMediaByGenreItemPreview(
    @PreviewParameter(MediaPreviewProvider::class) media: MediaUi
) {

    CompositionLocalProvider(
        LocalInspectionMode provides true,
    ) {
        PreviewContainer {
            TrendMediaByGenreItem(
                media = media,
                onEvent = {}
            )
        }
    }

}

@Preview(name = "GenreChip - Selected", showBackground = true)
@Composable
fun GenreChipSelectedPreview(
    @PreviewParameter(GenreTypePreviewProvider::class) genre: GenreType
) {
    PreviewContainer {
        GenreChip(
            genre = genre,
            isSelected = true,
            onEvent = {}
        )
    }
}

@Preview(name = "GenreChip - Unselected", showBackground = true)
@Composable
fun GenreChipUnselectedPreview(
    @PreviewParameter(GenreTypePreviewProvider::class) genre: GenreType
) {
    PreviewContainer {
        GenreChip(
            genre = genre,
            isSelected = false,
            onEvent = {}
        )
    }
}

@Preview(name = "GenreList", showBackground = true)
@Composable
fun GenreListPreview(
    @PreviewParameter(GenreListPreviewProvider::class) genreList: List<GenreType>
) {
    PreviewContainer {
        GenreList(
            genreList = genreList,
            selectedGenre = genreList.last(),
            onEvent = {}
        )
    }
}


