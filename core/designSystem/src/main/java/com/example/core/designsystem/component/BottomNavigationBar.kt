package com.example.core.designsystem.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.core.designsystem.theme.CinemaniaNavigationDefaults.navigationContentColor
import com.example.core.designsystem.theme.CinemaniaNavigationDefaults.navigationIndicatorColor
import com.example.core.designsystem.theme.CinemaniaNavigationDefaults.navigationSelectedItemColor

data class NavigationItem(
    val icon: ImageVector,
    val label: String,
    val onClick: (Int) -> Unit
)

@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    selectedIndex: Int,
    modifier: Modifier = Modifier
) {

    NavigationBar(
        modifier = modifier
            .navigationBarsPadding()
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
            ),
        containerColor = navigationContentColor()
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = index == selectedIndex,
                onClick = { item.onClick(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = navigationSelectedItemColor(),
                    indicatorColor = navigationIndicatorColor()
                )
            )
        }
    }
}