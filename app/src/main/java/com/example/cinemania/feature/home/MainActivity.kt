package com.example.cinemania.feature.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import com.example.cinemania.ui.theme.CinemaniaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CinemaniaTheme {
                Surface(tonalElevation = 5.dp) {
                    CinemaniaApp()
                }
            }
        }
    }
}
