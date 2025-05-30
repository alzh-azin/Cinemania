package com.example.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.core.domain.usecase.GetMedia
import com.example.core.ui.NavigationRoutes
import com.example.core.ui.model.toMediaUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMedia: GetMedia,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id =
        MutableStateFlow(savedStateHandle.toRoute<NavigationRoutes.Details>().id).asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val media = id.flatMapLatest { id ->
        getMedia(id = id).map { media ->
            media.toMediaUi()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = null
    )
}