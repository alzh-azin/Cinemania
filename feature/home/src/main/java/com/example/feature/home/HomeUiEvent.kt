package com.example.feature.home

import com.example.core.domain.model.GenreType

sealed class HomeUiEvent {

    data object Refresh : HomeUiEvent()

    data object GetData : HomeUiEvent()

    data class SelectGenre(val genre: GenreType) : HomeUiEvent()

    data class MediaClicked(val mediaId: Int) : HomeUiEvent()

}