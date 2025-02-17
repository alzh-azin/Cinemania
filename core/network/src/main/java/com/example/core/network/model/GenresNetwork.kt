package com.example.core.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenresNetwork(
    val id: Int,
    val name: String,
)
