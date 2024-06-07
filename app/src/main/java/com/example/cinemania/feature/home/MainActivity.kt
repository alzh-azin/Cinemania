package com.example.cinemania.feature.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.cinemania.ui.theme.CinemaniaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinemaniaTheme {
                Surface(
                    color = Color(0xFF101331),
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavGraph()
                }

            }
        }
    }
}
