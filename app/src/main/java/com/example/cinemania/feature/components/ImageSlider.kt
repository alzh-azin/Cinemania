package com.example.cinemania.feature.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.ceil

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(
    images: List<String?>,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }

    val itemSpacing = 16.dp
    val pagerState = rememberPagerState(initialPage = ceil(images.size / 2f).toInt() - 1) {
        images.size
    }

    LaunchedEffect(key1 = pagerState.settledPage) {
        delay(2000)
        pagerState.animateScrollToPage((pagerState.currentPage + 1) % images.size)
    }

    Box(modifier = modifier.fillMaxSize()) {


        HorizontalPager(
            state = pagerState, modifier = modifier,
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(0)
            ),
            contentPadding = PaddingValues(horizontal = 48.dp),
            pageSpacing = itemSpacing
        ) { currentPage ->
            val painter = rememberAsyncImagePainter(model = images[currentPage % images.size])

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(9 / 16f)
                    .padding(vertical = 64.dp)
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
                        .fillMaxSize()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            enabled = true,
                        ) {
                            scope.launch {
                                pagerState.animateScrollToPage(currentPage)
                            }
                        },
                    contentScale = ContentScale.FillBounds

                )
            }


        }


    }


//    val pagerState = rememberPagerState {
//        images.size
//    }
//
//    HorizontalPager(
//        state = pagerState,
//        modifier = modifier
//            .fillMaxSize(),
//
//        ) { currentPage ->
//
//        Card(
//            modifier = modifier
//                .fillMaxSize()
//                .padding(26.dp),
//            elevation = CardDefaults.cardElevation(8.dp)
//        ) {
//
//            val painter = rememberAsyncImagePainter(model = images[currentPage % images.size])
//
//            Image(
//                painter = painter,
//                contentDescription = null,
//                modifier = modifier
//                    .fillMaxSize(),
//                contentScale = ContentScale.FillBounds
//
//
//            )
//        }
//    }
}