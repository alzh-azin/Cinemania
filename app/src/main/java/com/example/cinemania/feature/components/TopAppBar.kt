package com.example.cinemania.feature.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navigateBackAvailable: Boolean,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    CenterAlignedTopAppBar(
        title = {
            TopAppBarTitle(
                title = "Cinemania",
                isVisible = !navigateBackAvailable
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
        ),
        navigationIcon = {
            if (navigateBackAvailable) {
                IconButton(onClick = {
                    onNavigationClick()
                }) {
                    TopAppBarIcon(
                        icon = Icons.AutoMirrored.Filled.ArrowBack,
                    )
                }

            }
        },
        actions = {
            if (navigateBackAvailable) {
                IconButton(onClick = {}) {
                    TopAppBarIcon(
                        icon = Icons.Outlined.FavoriteBorder,
                    )
                }
            } else {

                IconButton(onClick = { }) {
                    TopAppBarIcon(
                        icon = Icons.Filled.Search,
                    )

                }
            }
        }
    )
}

@Composable
fun TopAppBarIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(0.6f)
) {
    Icon(
        imageVector = icon,
        tint = iconTint,
        contentDescription = null,
        modifier = modifier
            .drawBehind {
                drawCircle(
                    color = backgroundColor,
                    radius = this.size.maxDimension
                )
            }
    )
}

@Composable
fun TopAppBarTitle(title: String, isVisible: Boolean) {
    if (isVisible) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displaySmall
        )
    }
}


