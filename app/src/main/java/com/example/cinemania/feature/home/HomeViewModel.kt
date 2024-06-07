package com.example.cinemania.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemania.core.domain.usecase.GetTrendMedia
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendMedia: GetTrendMedia
) : ViewModel() {

    val trendList = getTrendMedia(
        onStart = {},
        onComplete = {},
        onError = {}
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

}