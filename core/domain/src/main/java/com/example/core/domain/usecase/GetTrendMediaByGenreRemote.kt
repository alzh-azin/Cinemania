package com.example.core.domain.usecase

import com.example.core.domain.model.GenreType
import com.example.core.domain.repository.CinemaniaRepository
import javax.inject.Inject

class GetTrendMoviesByGenreRemote @Inject constructor(
    private val cinemaniaRepository: CinemaniaRepository
) {
    suspend operator fun invoke(genre: GenreType) =
        cinemaniaRepository.getTrendMoviesByGenreRemote(genre)
}