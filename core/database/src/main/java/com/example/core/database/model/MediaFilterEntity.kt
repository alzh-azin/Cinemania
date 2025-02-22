package com.example.core.database.model

import androidx.room.Entity

@Entity(primaryKeys = ["id", "category"])
data class MediaFilterEntity(
    val id: Int,
    val category: String,
    val index: Int,
)

fun MediaEntity.toMediaFilterEntity(
    filterTypeEntity: FilterTypeEntity,
    genreName: String? = null,
    index: Int
) = MediaFilterEntity(
    id = id,
    category = getCategory(filterTypeEntity, genreName),
    index = index
)

fun getCategory(
    filterTypeEntity: FilterTypeEntity,
    genreName: String?,
) = "${filterTypeEntity}${
    if (!genreName.isNullOrBlank()) {
        "-$genreName"
    } else
        ""
}"