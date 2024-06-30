package com.example.cinemania.feature.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.cinemania.core.domain.model.Media
import com.example.cinemania.ui.theme.amber600
import com.example.cinemania.ui.theme.lightBlue500
import com.example.cinemania.ui.theme.purpleA700
import com.example.cinemania.ui.theme.white

@Composable
fun DetailsRoute(
    modifier: Modifier = Modifier,
    detailsViewModel: DetailsViewModel = hiltViewModel()
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
            voteAverage = media?.voteAverage,
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
    voteAverage: Double?,
    genres: List<String>,
    releaseDate: String,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
    ) {

        DetailsHeaderImage(imageUrl = imageUrl)

        DetailsHeaderImageOverlay {

            DetailsHeaderCard {

                DetailsHeaderTitle(title, voteAverage)
                DetailsHeaderTagList(genres, releaseDate)

            }

        }

    }
}

@Composable
fun DetailsHeaderImage(
    imageUrl: String,
    modifier: Modifier = Modifier
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
}

@Composable
fun BoxScope.DetailsHeaderImageOverlay(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .align(Alignment.BottomCenter)
            .fillMaxHeight(0.2f)
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.background,
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
fun DetailsInfo(overview: String, modifier: Modifier = Modifier) {
    Text(
        text = overview,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .padding(16.dp)
    )
}

@Composable
fun DetailsHeaderCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 16.dp)
            .clip(shape = MaterialTheme.shapes.extraLarge)
            .background(
                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.9f),
                shape = MaterialTheme.shapes.extraLarge
            )
    ) {
        content()
    }
}

@Composable
fun DetailsHeaderTitle(
    title: String,
    voteAverage: Double?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.weight(1f)
        )
        if (voteAverage != null) {

            Text(
                //TODO - Move this to utils
                text = String.format("%.1f", voteAverage),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.labelLarge,
                modifier = modifier
            )
            Spacer(modifier = modifier.padding(2.dp))
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )

        }

    }
}

@Composable
fun DetailsHeaderTagList(
    genres: List<String>,
    releaseDate: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
    ) {

        if (genres.isNotEmpty()) {

            DetailsGenreTag(
                title = genres[0],
                backgroundColor = lightBlue500,
            )
        }

        if (genres.size > 1) {
            Spacer(modifier = modifier.padding(horizontal = 4.dp))
            DetailsGenreTag(
                title = genres[1],
                backgroundColor = purpleA700
            )
        }

        Spacer(modifier = modifier.padding(horizontal = 4.dp))
        DetailsGenreTag(
            title = extractYear(releaseDate).orEmpty(),
            backgroundColor = amber600
        )
    }

}

@Composable
fun DetailsGenreTag(
    title: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = title,
            modifier = modifier
                .clip(shape = CircleShape)
                .background(
                    backgroundColor,
                    shape = CircleShape
                )
                .padding(horizontal = 10.dp, vertical = 8.dp),
            color = white,
            style = MaterialTheme.typography.labelLarge,
        )

    }
}

//TODO move this function to utils
fun extractYear(dateString: String): String? {
    val regex = """^(\d{4})""".toRegex()
    val matchResult = regex.find(dateString)
    return matchResult?.groups?.get(1)?.value
}