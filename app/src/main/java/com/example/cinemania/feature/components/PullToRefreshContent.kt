package com.example.cinemania.feature.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PullToRefreshContent(
    items: List<T>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    val lazyListState = rememberLazyListState()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Allow vertical scroll (pull-to-refresh) only when scrolling down
                return if (available.y > 0) {
                    pullToRefreshState.nestedScrollConnection.onPreScroll(available, source)
                } else {
                    Offset.Zero
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                // Handle any remaining scroll after the child has consumed what it can
                return pullToRefreshState.nestedScrollConnection.onPostScroll(
                    consumed,
                    available,
                    source
                )
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                return pullToRefreshState.nestedScrollConnection.onPreFling(available)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                return pullToRefreshState.nestedScrollConnection.onPostFling(consumed, available)
            }
        }
    }

    Box(
        modifier = modifier
            .nestedScroll(nestedScrollConnection)
            .fillMaxSize()
    ) {
        content()

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                Log.d("TestRefresh", "onRefresh, Loading $isRefreshing ")
                onRefresh()
            }
        }

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullToRefreshState.startRefresh()
                Log.d("TestRefresh", "start refresh, Loading $isRefreshing ")
            } else {
                pullToRefreshState.endRefresh()
                Log.d("TestRefresh", "end refresh, Loading $isRefreshing ")
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter),
        )

    }


}