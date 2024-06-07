package com.example.cinemania.core.domain.usecase

import com.example.cinemania.core.domain.repository.CinemaniaRepository
import javax.inject.Inject

class GetMedia @Inject constructor(
    private val cinemaniaRepository: CinemaniaRepository
) {
    operator fun invoke(
        id: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = cinemaniaRepository.getMedia(
        id = id,
        onStart = onStart,
        onComplete = onComplete,
        onError = onError
    )
}
