package com.example.core.domain.usecase

import com.example.core.domain.repository.CinemaniaRepository
import javax.inject.Inject

class SearchMediaRemote @Inject constructor(
    private val cinemaniaRepository: CinemaniaRepository
) {
    suspend operator fun invoke(query: String, pageSize: Int) =
        cinemaniaRepository.searchMediaRemote(query, pageSize)
}