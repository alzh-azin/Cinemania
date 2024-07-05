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
import com.example.cinemania.feature.components.CinemaniaNavigationBar
import com.example.cinemania.feature.components.NavigationBarScreens
import com.example.cinemania.feature.components.TopAppBar

@Composable
fun CinemaniaApp(
    modifier: Modifier = Modifier,
) {

    val navController: NavHostController = rememberNavController()

    var navigateBackAvailable by remember {
        mutableStateOf(false)
    }

    var navigationBarAvailable by remember {
        mutableStateOf(true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigateBackAvailable = navigateBackAvailable,
                onNavigationClick = { navController.navigateUp() }

            )
        },
        bottomBar = {
            val items = listOf(
                NavigationBarScreens.Home,
                NavigationBarScreens.Search,
                NavigationBarScreens.Favorites
            )
            if (navigationBarAvailable)
                CinemaniaNavigationBar(navController = navController, items = items)
        },
        content = { padding ->
            NavGraph(
                contentPadding = padding,
                onNavigateBackAvailable = { isAvailable ->
                    navigateBackAvailable = isAvailable
                },
                onNavigateBarAvailable = { isAvailable ->
                    navigationBarAvailable = isAvailable
                },
                navController = navController
            )
        }
    )
}