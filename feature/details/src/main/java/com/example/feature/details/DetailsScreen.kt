package com.example.feature.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.cinemania.core.designSystem.R
import com.example.core.designsystem.theme.amber600
import com.example.core.designsystem.theme.lightBlue500
import com.example.core.designsystem.theme.purpleA700
import com.example.core.designsystem.theme.white
import com.example.core.common.utils.CinemaniaConstants.BASE_IMAGE_URL
import com.example.core.common.utils.CinemaniaConstants.BASE_IMAGE_URL_HIGH_QUALITY
import com.example.core.common.utils.extractYear
import com.example.core.designsystem.preview.PreviewContainer
import com.example.core.ui.model.MediaUi

@Composable
fun DetailsRoute(
    contentPadding: PaddingValues,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {

    val media by detailsViewModel.media.collectAsStateWithLifecycle()

    DetailsScreen(
        media = media,
        contentPadding = contentPadding
    )
}

@Composable
fun DetailsScreen(
    media: MediaUi?,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = contentPadding.calculateBottomPadding())
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
    val painter = if (!LocalInspectionMode.current) {

        rememberAsyncImagePainter(model = "$BASE_IMAGE_URL$BASE_IMAGE_URL_HIGH_QUALITY" + imageUrl)
    } else {
        painterResource(R.drawable.ic_preview_placeholder)
    }

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
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .height(
                WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding() * 1.5f
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
                        Color.Transparent

                    )
                )
            )
    ) {
    }

    Box(
        modifier = modifier
            .align(Alignment.BottomCenter)
            .fillMaxHeight(0.2f)
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
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
            .padding(top = 20.dp, end = 8.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        if (voteAverage != null) {

            Text(
                //TODO - Move this to utils
                text = String.format("%.1f", voteAverage),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(modifier = Modifier.padding(2.dp))
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
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            DetailsGenreTag(
                title = genres[1],
                backgroundColor = purpleA700
            )
        }

        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
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

@Preview(name = "DetailsScreen", showBackground = true)
@Composable
fun DetailsScreenPreview(
    @PreviewParameter(MediaPreviewProvider::class) media: MediaUi
) {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        PreviewContainer {
            DetailsScreen(
                media = media,
                contentPadding = PaddingValues(0.dp)
            )
        }
    }
}

@Preview(name = "DetailsHeader", showBackground = true)
@Composable
fun DetailsHeaderPreview(
    @PreviewParameter(MediaPreviewProvider::class) media: MediaUi
) {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        PreviewContainer {
            DetailsHeader(
                imageUrl = media.posterPath.orEmpty(),
                title = media.title.orEmpty(),
                voteAverage = media.voteAverage,
                genres = media.genres ?: emptyList(),
                releaseDate = media.releaseDate.orEmpty()
            )
        }
    }
}

@Preview(name = "DetailsHeaderCard", showBackground = true)
@Composable
fun DetailsHeaderCardPreview(
    @PreviewParameter(MediaPreviewProvider::class) media: MediaUi
) {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        PreviewContainer {
            DetailsHeaderCard {
                DetailsHeaderTitle(
                    title = media.title.orEmpty(),
                    voteAverage = media.voteAverage
                )
                DetailsHeaderTagList(
                    genres = media.genres ?: emptyList(),
                    releaseDate = media.releaseDate.orEmpty()
                )
            }
        }
    }
}

@Preview(name = "DetailsHeaderTagList", showBackground = true)
@Composable
fun DetailsHeaderTagListPreview(
    @PreviewParameter(MediaPreviewProvider::class) media: MediaUi
) {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        PreviewContainer {
            DetailsHeaderTagList(
                genres = media.genres ?: emptyList(),
                releaseDate = media.releaseDate.orEmpty()
            )
        }
    }
}

@Preview(name = "DetailsGenreTag", showBackground = true)
@Composable
fun DetailsGenreTagPreview() {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        PreviewContainer {
            DetailsGenreTag(
                title = "Fantasy",
                backgroundColor = Color(0xFF6200EE)
            )
        }
    }
}
