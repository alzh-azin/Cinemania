package com.example.cinemania.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cinemania.core.domain.usecase.GetMedia
import com.example.cinemania.feature.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMedia: GetMedia,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id =
        MutableStateFlow(savedStateHandle.toRoute<NavigationRoutes.Details>().id).asStateFlow()

    val media = id.flatMapLatest { id ->
        getMedia(
            id = id,
            onStart = {},
            onComplete = {},
            onError = {}
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = null
    )
}