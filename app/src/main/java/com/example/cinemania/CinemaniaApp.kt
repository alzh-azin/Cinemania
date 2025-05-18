package com.example.cinemania

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cinemania.navigation.NavGraph
import com.example.core.designsystem.component.BottomNavigationBar
import com.example.core.ui.NavigationRoutes
import com.example.core.ui.NavigationBarRoute
import com.example.core.designsystem.component.TopAppBar

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
        modifier = modifier,
        topBar = {
            if (topAppBarAvailable) {
                TopAppBar(
                    title = stringResource(R.string.app_name),
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
                NavigationBarRoute.Home,
                NavigationBarRoute.Search,
                NavigationBarRoute.Favorites
            )

            var selectedItemIndex by rememberSaveable {
                mutableIntStateOf(0)
            }

            if (navigationBarAvailable)
                BottomNavigationBar(
                    items = items.map { bottomNavItem ->
                        bottomNavItem.toNavigationItem(
                            onClick = { index ->
                                selectedItemIndex = index
                                navController.navigate(bottomNavItem.route)
                            }
                        )
                    },
                    selectedIndex = selectedItemIndex
                )
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