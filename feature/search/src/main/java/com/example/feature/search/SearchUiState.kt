package com.example.feature.search

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.example.core.domain.model.Media
import com.example.core.ui.model.MediaUi
import kotlinx.coroutines.flow.Flow

@Stable
data class SearchUiState(
    val searchQuery: String = "",
    val searchResult: Flow<PagingData<MediaUi>>? = null,
    val emptyResult: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val showPaginationError: Boolean = false
)
