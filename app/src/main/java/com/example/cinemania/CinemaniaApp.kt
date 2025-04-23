package com.example.cinemania

import androidx.compose.foundation.Image
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
import com.example.cinemania.navigation.NavGraph
import com.example.core.ui.NavigationRoutes
import com.example.feature.component.CinemaniaNavigationBar
import com.example.feature.component.NavigationBarScreens
import com.example.feature.component.TopAppBar

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

    var topAppBarAvailable by remember {
        mutableStateOf(true)
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    var showErrorSnackBar by remember {
        mutableStateOf(false)
    }

    var snackBarResult by remember {
        mutableStateOf(SnackbarResult.Dismissed)
    }

    val networkConnectionError = stringResource(id = R.string.msg_network_error)
    val snackBarActionLabel = stringResource(id = R.string.label_retry)
    LaunchedEffect(key1 = showErrorSnackBar) {

        if (showErrorSnackBar) {
            val result = snackBarHostState.showSnackbar(
                message = networkConnectionError,
                actionLabel = snackBarActionLabel,
                withDismissAction = true,
                duration = Indefinite
            )

            snackBarResult = result

            if (result == SnackbarResult.Dismissed) {
                snackBarHostState.currentSnackbarData?.dismiss()
            }

            showErrorSnackBar = false
        }
    }

    Scaffold(
        topBar = {
            if (topAppBarAvailable) {
                TopAppBar(
                    navigateBackAvailable = navigateBackAvailable,
                    onNavigationClick = { navController.navigateUp() },
                    onSearchClick = {
                        navController.navigate(NavigationRoutes.Search)
                    }
                )
            }
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
                onTopAppBarAvailable = { isAvailable ->
                    topAppBarAvailable = isAvailable
                },
                onNavigateBackAvailable = { isAvailable ->
                    navigateBackAvailable = isAvailable
                },
                onNavigateBarAvailable = { isAvailable ->
                    navigationBarAvailable = isAvailable
                },
                onNetworkConnectionError = { showError ->
                    showErrorSnackBar = showError

                    if (!showError)
                        snackBarResult = SnackbarResult.Dismissed
                },
                navController = navController,
                snackBarResult = snackBarResult
            )
        }
    )
}