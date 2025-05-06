package com.example.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import com.example.cinemania.core.designSystem.R
import com.example.core.common.utils.CinemaniaConstants.BASE_IMAGE_URL
import com.example.core.common.utils.CinemaniaConstants.BASE_IMAGE_URL_HIGH_QUALITY
import kotlin.math.absoluteValue
import kotlin.math.ceil

data class SliderItem(
    val id: Int,
    val imageUrl: String
)

@Composable
fun ImageSlider(
    images: List<SliderItem>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = ceil(images.size / 2f).toInt() - 1) {
        images.size
    }

    Box {

        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .padding(vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            contentPadding = PaddingValues(horizontal = 90.dp),
            pageSpacing = 16.dp
        ) { currentPage ->
            val painter =
                if (!LocalInspectionMode.current) {
                    rememberAsyncImagePainter(
                        model =
                        "$BASE_IMAGE_URL$BASE_IMAGE_URL_HIGH_QUALITY"
                                + images[currentPage % images.size].imageUrl
                    )
                } else {
                    painterResource(R.drawable.ic_preview_placeholder)
                }

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(6 / 8f)
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
                    }
                    .clickable {
                        images[currentPage % images.size].let { onItemClick(it.id) }
                    },
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