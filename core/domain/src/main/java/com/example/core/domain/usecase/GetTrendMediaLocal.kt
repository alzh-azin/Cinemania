package com.example.core.domain.usecase

import com.example.core.domain.repository.CinemaniaRepository
import javax.inject.Inject

class GetTrendMediaLocal @Inject constructor(
    private val cinemaniaRepository: CinemaniaRepository
) {
    operator fun invoke() = cinemaniaRepository.getTrendMediaLocal()
}