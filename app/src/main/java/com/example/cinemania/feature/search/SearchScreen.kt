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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
            onEvent(SearchUiEvent.refresh)
            onNetworkConnectionError(false)
        }
    }

    PullToRefreshContent(
        isRefreshing = searchUiState.isLoading,
        contentPadding = contentPadding,
        onRefresh = {
            onEvent(SearchUiEvent.refresh)
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
    val focusManager = LocalFocusManager.current
    var isFocused by rememberSaveable { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                focusManager.clearFocus()
                isFocused = false
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .onFocusEvent { focusState ->
                isFocused = focusState.isFocused
            },
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        value = query,
        onValueChange = {
            onEvent(SearchUiEvent.onSearchQueryChange(it))
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
                    onEvent(SearchUiEvent.onClearSearch)
                    focusManager.clearFocus()
                }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
        }
    )

    LaunchedEffect(isFocused) {
        if (!isFocused) {
            focusManager.clearFocus()
        }
    }
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

    val searchList = searchResult?.collectAsLazyPagingItems()

    LaunchedEffect(key1 = searchList?.loadState) {

        if (searchList?.loadState?.append is LoadState.Error) {
            onEvent(SearchUiEvent.showPaginationError)

        }
        if (searchList?.loadState?.append is LoadState.Loading) {

            onEvent(SearchUiEvent.startPaginationLoading)
        }
        if (searchList?.loadState?.append is LoadState.NotLoading) {
            onEvent(SearchUiEvent.stopPaginationLoading)
        }

        if (searchList?.loadState?.refresh is LoadState.Loading) {
            onEvent(SearchUiEvent.startLoading)
        }
        if (searchList?.loadState?.refresh is LoadState.NotLoading) {
            onEvent(SearchUiEvent.stopLoading)
        }
        if (searchList?.loadState?.refresh is LoadState.Error) {
            onEvent(SearchUiEvent.onError)
            onEvent(SearchUiEvent.stopLoading)
        }
    }

    if (searchList?.itemCount == 0
        && searchList.loadState.source.isIdle
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
                count = searchList?.itemCount ?: 0,
                key = { index ->
                    searchList?.peek(index)?.id ?: 0
                }
            ) { index ->
                val media = searchList?.get(index)

                if (media != null) {
                    SearchItem(
                        title = media.title.orEmpty(),
                        image = media.posterPath,
                        voteAverage = media.voteAverage,
                        releaseDate = media.releaseDate,
                        onNavigateToDetailsScreen = {
                            onEvent(SearchUiEvent.navigateToDetailsScreen(media))
                            onNavigateToDetailsScreen(media.id)
                        }
                    )
                }
            }
            if (isLoadingNextPage && searchList?.itemCount?.rem(CinemaniaConstants.PAGE_SIZE) == 0) {
                item { LoadingItem() }
            }
            if (showPaginationError) {
                item {
                    ErrorMessage(message = "Please try again") {
                        searchList?.retry()
                    }
                }
            }
        }

    }
}

@Composable
fun LoadingItem(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    //TODO change the design

    Row(
        modifier = modifier.padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f),
            maxLines = 2
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Retry")
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