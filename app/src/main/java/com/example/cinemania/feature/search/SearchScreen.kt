package com.example.cinemania.feature.search

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.cinemania.R
import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.feature.components.PullToRefreshContent
import com.example.cinemania.feature.details.extractYear
import com.example.cinemania.ui.theme.CinemaniaTheme

@Composable
fun SearchRoute(
    contentPadding: PaddingValues,
    onNetworkConnectionError: (showError: Boolean) -> Unit,
    snackBarResult: SnackbarResult,
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
    onNetworkConnectionError: (showError: Boolean) -> Unit,
    onEvent: (SearchUiEvent) -> Unit,
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

            SearchResultList(
                emptyResult = searchUiState.emptyResult,
                searchResult = searchUiState.searchResult
            )
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
            if (it.isEmpty())
                onEvent(SearchUiEvent.onClearSearch)
            else
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
                }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
        })

}

@Composable
fun SearchResultList(
    emptyResult: Boolean,
    searchResult: List<Media>,
    modifier: Modifier = Modifier
) {

    if (emptyResult) {
        Text(
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(),
            text = stringResource(id = R.string.msg_no_result)
        )
    } else {
        LazyColumn {
            items(searchResult) { item ->
                SearchItem(
                    title = item.title.orEmpty(),
                    image = item.posterPath,
                    voteAverage = item.voteAverage,
                    releaseDate = item.releaseDate
                )
            }
        }
    }
}

@Composable
fun SearchItem(
    title: String?,
    image: String?,
    voteAverage: Double?,
    releaseDate: String?,
    modifier: Modifier = Modifier
) {

    val painter = rememberAsyncImagePainter(model = image)

    Row(modifier = modifier.padding(start = 8.dp, top = 16.dp)) {

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
            releaseDate = "2024"
        )
    }
}