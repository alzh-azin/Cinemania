package com.example.cinemania.core.domain.usecase

import com.example.cinemania.core.domain.repository.CinemaniaRepository
import com.example.core.domain.model.Media
import javax.inject.Inject

class InsertMedia @Inject constructor(
    private val cinemaniaRepository: CinemaniaRepository
) {
    suspend operator fun invoke(media: Media) = cinemaniaRepository.insertMedia(media)
}