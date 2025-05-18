package com.example.core.designsystem.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.core.designsystem.theme.CinemaniaTheme
import androidx.compose.ui.unit.dp

@Composable
fun PreviewContainer(
    modifier: Modifier = Modifier,
    tonalElevation: Dp = 5.dp,
    content: @Composable () -> Unit
) {
    CinemaniaTheme {
        Surface(
            tonalElevation = tonalElevation,
            modifier = modifier
        ) {
            content()
        }
    }
}