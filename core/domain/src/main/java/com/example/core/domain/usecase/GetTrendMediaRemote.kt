package com.example.core.domain.usecase

import com.example.core.domain.repository.CinemaniaRepository
import javax.inject.Inject

class GetTrendMediaRemote @Inject constructor(
    private val cinemaniaRepository: CinemaniaRepository
) {
    suspend operator fun invoke() = cinemaniaRepository.getTrendMediaRemote()
}