package com.example.cinemania.feature.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navigateBackAvailable: Boolean,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            if (!navigateBackAvailable)
                Text(text = "Cinemania")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White
        ),
        navigationIcon = {
            if (navigateBackAvailable) {
                IconButton(onClick = {
                    onNavigationClick()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = modifier.drawBehind {
                            drawCircle(
                                color = Color.Gray.copy(alpha = 0.4f),
                                radius = this.size.maxDimension
                            )
                        }
                    )
                }

            }
        },
        actions = {
            if (navigateBackAvailable) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = modifier
                            .drawBehind {
                                drawCircle(
                                    color = Color.Gray.copy(alpha = 0.4f),
                                    radius = this.size.maxDimension
                                )
                            }
                    )
                }
            } else {

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = modifier
                            .drawBehind {
                                drawCircle(
                                    color = Color.Gray.copy(alpha = 0.4f),
                                    radius = this.size.maxDimension
                                )
                            }
                    )

                }
            }
        }
    )
}


