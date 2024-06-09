package com.example.cinemania.feature.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.cinemania.core.domain.model.Media

@Composable
fun DetailsRoute(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val media by detailsViewModel.media.collectAsStateWithLifecycle()

    DetailsScreen(
        media = media,
        modifier = modifier
    )
}

@Composable
fun DetailsScreen(
    media: Media?,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DetailsHeader(
            imageUrl = media?.posterPath.orEmpty(),
            title = media?.title.orEmpty(),
            type = media?.mediaType.orEmpty(),
            releaseDate = media?.releaseDate.orEmpty(),
            genres = media?.genres.orEmpty()
        )

        DetailsInfo(media?.overview.orEmpty())
    }
}

@Composable
fun DetailsHeader(
    imageUrl: String,
    title: String,
    type: String,
    genres: List<String>,
    releaseDate: String,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
    ) {

        val painter = rememberAsyncImagePainter(model = imageUrl)
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(3 / 4f)
        )
        Box(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .fillMaxHeight(0.2f)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFF101331).copy(alpha = 0.5f),
                            Color(0xFF101331).copy(alpha = 0.9f),
                            Color(0xFF101331),
                        )
                    )
                )
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        top = 40.dp,
                        start = 16.dp
                    )
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    style = TextStyle(
                        lineHeight = 1.em
                    )
                )
                Spacer(modifier = modifier.padding(vertical = 4.dp))

                Text(
                    text = "$type / ${genres.joinToString("/")} / $releaseDate",
                    color = Color.Gray,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun DetailsInfo(overview: String, modifier: Modifier = Modifier) {
    Text(
        text = overview,
        color = Color.White,
        fontSize = 20.sp,
        style = TextStyle(
            lineHeight = 1.5.em
        ),
        modifier = modifier
            .padding(16.dp)
    )
}