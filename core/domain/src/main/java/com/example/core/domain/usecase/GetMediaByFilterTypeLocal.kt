package com.example.core.domain.usecase

import com.example.core.domain.model.FilterType
import com.example.core.domain.model.GenreType
import com.example.core.domain.repository.CinemaniaRepository
import javax.inject.Inject

class GetMediaByFilterTypeLocal @Inject constructor(
    private val cinemaniaRepository: CinemaniaRepository
) {
    operator fun invoke(genre: GenreType? = null, filterType: FilterType) =
        cinemaniaRepository.getMediaByFilterTypeLocal(genre, filterType)
}