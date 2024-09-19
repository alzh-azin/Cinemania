package com.example.cinemania.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.cinemania.R
import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.core.network.utils.UrlHelper.BASE_IMAGE_URL
import com.example.cinemania.core.network.utils.UrlHelper.BASE_IMAGE_URL_LOW_QUALITY
import com.example.cinemania.core.utils.CinemaniaConstants
import com.example.cinemania.feature.components.PullToRefreshContent
import com.example.cinemania.feature.details.extractYear
import com.example.cinemania.ui.theme.CinemaniaTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchRoute(
    contentPadding: PaddingValues,
    snackBarResult: SnackbarResult,
    onNetworkConnectionError: (showError: Boolean) -> Unit,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val searchUiState by searchViewModel.searchUiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        searchViewModel.searchUiEffect.collect {
            onNetworkConnectionError(true)
        }
    }

    SearchScreen(
        searchUiState = searchUiState,
        contentPadding = contentPadding,
        snackBarResult = snackBarResult,
        onNetworkConnectionError = onNetworkConnectionError,
        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
        onEvent = { event ->
            searchViewModel.onEvent(event)
        },
        modifier = modifier
    )
}

@Composable
fun SearchScreen(
    searchUiState: SearchUiState,
    contentPadding: PaddingValues,
    snackBarResult: SnackbarResult,
    onEvent: (SearchUiEvent) -> Unit,
    onNetworkConnectionError: (showError: Boolean) -> Unit,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(key1 = snackBarResult) {
        if (snackBarResult == SnackbarResult.ActionPerformed) {
            onEvent(SearchUiEvent.Refresh)
            onNetworkConnectionError(false)
        }
    }

    PullToRefreshContent(
        isRefreshing = searchUiState.isLoading,
        contentPadding = contentPadding,
        onRefresh = {
            onEvent(SearchUiEvent.Refresh)
        }) {
        Column {

            SearchTextField(query = searchUiState.searchQuery, onEvent)

            if (searchUiState.searchQuery.isNotBlank()) {

                SearchResultList(
                    isLoadingNextPage = searchUiState.isLoadingNextPage,
                    showPaginationError = searchUiState.showPaginationError,
                    searchResult = searchUiState.searchResult,
                    onEvent = onEvent,
                    onNavigateToDetailsScreen = onNavigateToDetailsScreen
                )
            }
        }
    }
}

@Composable
fun SearchTextField(
    query: String,
    onEvent: (SearchUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        value = query,
        onValueChange = {
            onEvent(SearchUiEvent.ChangeSearchQuery(it))
        },
        label = { Text(text = stringResource(id = R.string.label_search)) },
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
        },
        trailingIcon = {
            if (query.isNotEmpty())
                IconButton(onClick = {
                    onEvent(SearchUiEvent.ClearSearch)
                }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
        }
    )
}

@Composable
fun SearchResultList(
    isLoadingNextPage: Boolean,
    showPaginationError: Boolean,
    searchResult: Flow<PagingData<Media>>?,
    onEvent: (SearchUiEvent) -> Unit,
    onNavigateToDetailsScreen: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val pagingItem = searchResult?.collectAsLazyPagingItems()

    PaginationStateHandler(loadState = pagingItem?.loadState, onEvent = onEvent)

    if (pagingItem?.itemCount == 0
        && pagingItem.loadState.source.isIdle
    ) {
        Text(
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(),
            text = stringResource(id = R.string.msg_no_result)
        )
    } else {
        LazyColumn {
            items(
                count = pagingItem?.itemCount ?: 0,
                key = { index ->
                    pagingItem?.peek(index)?.id ?: 0
                }
            ) { index ->
                val media = pagingItem?.get(index)

                if (media != null) {
                    SearchItem(
                        title = media.title.orEmpty(),
                        image = media.posterPath,
                        voteAverage = media.voteAverage,
                        releaseDate = media.releaseDate,
                        onNavigateToDetailsScreen = {
                            onEvent(SearchUiEvent.NavigateToDetailsScreen(media))
                            onNavigateToDetailsScreen(media.id)
                        }
                    )
                }
            }
            // If itemCount % 20 is not 0, it means itemCount for current page is less than 20
            // and there is no more pages for that search query, so LoadingItem should not appear
            if (isLoadingNextPage && pagingItem?.itemCount?.rem(CinemaniaConstants.PAGE_SIZE) == 0) {
                item { PaginationLoadingItem() }
            }
            if (showPaginationError) {
                item {
                    PaginationErrorItem {
                        pagingItem?.retry()
                    }
                }
            }
        }

    }
}

@Composable
fun PaginationStateHandler(loadState: CombinedLoadStates?, onEvent: (SearchUiEvent) -> Unit) {

    LaunchedEffect(key1 = loadState) {

        if (loadState?.append is LoadState.Error) {
            onEvent(SearchUiEvent.ShowPaginationError)

        }
        if (loadState?.append is LoadState.Loading) {

            onEvent(SearchUiEvent.StartPaginationLoading)
        }
        if (loadState?.append is LoadState.NotLoading) {
            onEvent(SearchUiEvent.StopPaginationLoading)
        }

        if (loadState?.refresh is LoadState.Loading) {
            onEvent(SearchUiEvent.StartLoading)
        }
        if (loadState?.refresh is LoadState.NotLoading) {
            onEvent(SearchUiEvent.StopLoading)
        }
        if (loadState?.refresh is LoadState.Error) {
            onEvent(SearchUiEvent.ShowSearchResultError)
            onEvent(SearchUiEvent.StopLoading)
        }
    }
}

@Composable
fun PaginationLoadingItem(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun PaginationErrorItem(
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier.padding(end = 8.dp),
            text = stringResource(id = R.string.msg_network_error),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(
                text = stringResource(id = R.string.label_retry),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun SearchItem(
    title: String?,
    image: String?,
    voteAverage: Double?,
    releaseDate: String?,
    onNavigateToDetailsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {

    val painter =
        rememberAsyncImagePainter(model = BASE_IMAGE_URL + BASE_IMAGE_URL_LOW_QUALITY + image)

    Row(modifier = modifier
        .padding(start = 8.dp, top = 16.dp)
        .clickable {
            onNavigateToDetailsScreen()
        }) {

        Image(
            painter = painter,
            contentDescription = null,
            modifier = modifier
                .width(100.dp)
                .aspectRatio(6 / 8f)
        )

        Column(modifier = modifier.padding(top = 24.dp, start = 8.dp, end = 8.dp)) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                text = title.orEmpty(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = modifier.padding(4.dp))

            Row {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = modifier.padding(2.dp))
                //TODO - Move this to utils
                Text(
                    text = String.format("%.1f", voteAverage),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = modifier.wrapContentHeight()
                )
            }

            Spacer(modifier = modifier.padding(4.dp))
            Text(
                text = extractYear(releaseDate.orEmpty()).orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(name = "SearchItem")
@Composable
fun SearchItemPreview() {
    CinemaniaTheme {
        SearchItem(
            title = "Inside out 2",
            image = "",
            voteAverage = 7.9,
            releaseDate = "2024",
            onNavigateToDetailsScreen = {}
        )
    }
}

@Preview(name = "PaginationErrorItem")
@Composable
fun PaginationErrorItemPreview() {
    CinemaniaTheme {
        PaginationErrorItem {

        }
    }
}