package com.example.cinemania.core.database.model

import androidx.room.TypeConverter

class GenreTypeConvertor {

    @TypeConverter
    fun fromGenres(value: String?): List<Int>? {
        return value?.split(",")?.map {
            it.toInt()
        }
    }

    @TypeConverter
    fun toGenres(list: List<Int>?): String {
        return list?.joinToString(",").orEmpty()
    }
}