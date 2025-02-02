package com.example.core.domain.usecase

import com.example.core.domain.model.Media
import com.example.core.domain.repository.CinemaniaRepository
import javax.inject.Inject

class InsertMedia @Inject constructor(
    private val cinemaniaRepository: CinemaniaRepository
) {
    suspend operator fun invoke(media: Media) = cinemaniaRepository.insertMedia(media)
}