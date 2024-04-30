package com.example.cinemania.core.domain.usecase

import com.example.cinemania.core.domain.repository.CinemaniaRepository
import javax.inject.Inject

class GetTrendMedia @Inject constructor(
    private val cinemaniaRepository: CinemaniaRepository
) {
    operator fun invoke(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = cinemaniaRepository.getTrendingMedia(
        onStart = onStart,
        onComplete = onComplete,
        onError = onError
    )
}