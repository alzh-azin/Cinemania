package com.example.core.domain.usecase

import com.example.core.domain.repository.CinemaniaRepository
import javax.inject.Inject

class GetMedia @Inject constructor(
    private val cinemaniaRepository: CinemaniaRepository
) {
    operator fun invoke(id: Int) = cinemaniaRepository.getMedia(id = id)
}
