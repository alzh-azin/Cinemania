package com.example.cinemania.feature.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinemania.core.domain.model.Media

@Composable
fun HomeRoute(homeViewModel: HomeViewModel = hiltViewModel()) {

    val trendMedia by homeViewModel.trendList.collectAsStateWithLifecycle()

    HomeScreen(trendMedia)
}

@Composable
fun HomeScreen(trendMedia: List<Media>) {

    TrendMediaList(trendMedia)
}

@Composable
fun TrendMediaList(
    trendMedia: List<Media>,
    modifier: Modifier = Modifier,
) {

    LazyColumn(modifier) {
        items(items = trendMedia, key = { media -> media.id }) {
            MediaItem(name = it.title)
        }
    }
}

@Composable
fun MediaItem(
    name: String?,
    modifier: Modifier = Modifier
) {
    Row(modifier.padding(top = 16.dp)) {
        Text(text = name ?: "empty")
    }
}