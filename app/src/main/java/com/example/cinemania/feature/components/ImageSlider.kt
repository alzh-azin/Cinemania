package com.example.cinemania.feature.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.ceil

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(
    images: List<String?>,
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState(initialPage = ceil(images.size / 2f).toInt() - 1) {
        images.size
    }
    val autoScrollJob = remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .collect { page ->

                autoScrollJob.value?.cancel()
                autoScrollJob.value = scope.launch {
                    delay(2000)
                    val nextPage = (page + 1) % images.size
                    pagerState.animateScrollToPage(nextPage)
                }
            }

    }

    Box(modifier = modifier) {

        HorizontalPager(
            state = pagerState, modifier = modifier.padding(vertical = 16.dp),
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(0)
            ),
            contentPadding = PaddingValues(horizontal = 48.dp),
            pageSpacing = 16.dp
        ) { currentPage ->
            val painter = rememberAsyncImagePainter(model = images[currentPage % images.size])

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(6 / 9f)
                    .graphicsLayer {
                        val pageOffSet = (
                                (pagerState.currentPage - currentPage) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                        )
                        scaleY = lerp(
                            start = 0.75f,
                            stop = 1f,
                            fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                        )
                    },
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds

                )
            }

        }
    }
}