package com.example.cinemania.feature.home

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cinemania.R
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

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    var isOffline by remember {
        mutableStateOf(false)
    }

    val networkConnectionError = stringResource(id = R.string.msg_network_error)
    val snackBarActionLabel = stringResource(id = R.string.label_retry)
    LaunchedEffect(key1 = isOffline) {
        if (isOffline) {
            val result = snackBarHostState.showSnackbar(
                message = networkConnectionError,
                actionLabel = snackBarActionLabel,
                withDismissAction = true,
                duration = Indefinite
            )

            when (result) {
                SnackbarResult.ActionPerformed -> {

                }

                SnackbarResult.Dismissed -> {
                    snackBarHostState.currentSnackbarData?.dismiss()
                }
            }
        }
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
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
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
                onNetworkConnectionError = { isConnected ->
                    isOffline = !isConnected
                },
                navController = navController
            )
        }
    )
}