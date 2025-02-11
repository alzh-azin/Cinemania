package com.example.feature.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.core.designsystem.theme.CinemaniaNavigationDefaults.navigationContentColor
import com.example.core.designsystem.theme.CinemaniaNavigationDefaults.navigationIndicatorColor
import com.example.core.designsystem.theme.CinemaniaNavigationDefaults.navigationSelectedItemColor
import com.example.core.ui.NavigationRoutes

enum class NavigationBarTypes {
    Home,
    Discover,
    Favorites
}

sealed class NavigationBarScreens(
    val title: NavigationBarTypes,
    val icon: ImageVector,
    val route: NavigationRoutes
) {

    data object Home :
        NavigationBarScreens(
            title = NavigationBarTypes.Home,
            icon = Icons.Filled.Home,
            route = NavigationRoutes.Home
        )

    data object Search :
        NavigationBarScreens(
            title = NavigationBarTypes.Discover,
            icon = Icons.Filled.Search,
            route = NavigationRoutes.Search
        )

    data object Favorites :
        NavigationBarScreens(
            title = NavigationBarTypes.Favorites,
            icon = Icons.Filled.Favorite,
            route = NavigationRoutes.Favorites
        )
}

@Composable
fun CinemaniaNavigationBar(
    navController: NavController,
    items: List<NavigationBarScreens>,
    modifier: Modifier = Modifier
) {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar(
        modifier = modifier
            .navigationBarsPadding()
            .windowInsetsPadding(
                WindowInsets.safeDrawing
                    .only(WindowInsetsSides.Bottom)
            ),
        containerColor = navigationContentColor()
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                label = {
                    Text(text = item.title.name)
                },
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = navigationSelectedItemColor(),
                    indicatorColor = navigationIndicatorColor()
                )
            )
        }
    }
}