package com.example.cinemania.feature.home


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cinemania.feature.components.TopAppBar

@Composable
fun CinemaniaApp(
    modifier: Modifier = Modifier,
) {

    val navController: NavHostController = rememberNavController()

    var navigateBackAvailable by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigateBackAvailable = navigateBackAvailable,
                onNavigationClick = { navController.navigateUp() }

            )
        },
        content = { padding ->
            NavGraph(
                onNavigateBackAvailable = { isAvailable ->
                    navigateBackAvailable = isAvailable
                },
                navController = navController
            )
        }
    )
}