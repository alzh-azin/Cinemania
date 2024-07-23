package com.example.cinemania.core.domain.usecase

import com.example.cinemania.core.domain.repository.CinemaniaRepository
import javax.inject.Inject

class GetTrendMedia @Inject constructor(
    private val cinemaniaRepository: CinemaniaRepository
) {
    operator fun invoke() = cinemaniaRepository.getTrendingMedia()
}