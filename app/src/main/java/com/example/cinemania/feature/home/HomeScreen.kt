package com.example.cinemania.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.feature.components.ImageSlider

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val trendMedia by homeViewModel.trendList.collectAsStateWithLifecycle()

    HomeScreen(
        trendMedia,
        navController
    )
}

@Composable
fun HomeScreen(
    trendMedia: List<Media>,
    navController: NavHostController = rememberNavController()
) {

    if (trendMedia.isNotEmpty())
        ImageSlider(
            trendMedia.map {
                it.posterPath
            },
            navController
        )
}