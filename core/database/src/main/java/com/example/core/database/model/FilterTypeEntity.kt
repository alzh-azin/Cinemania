package com.example.core.database.model

import com.example.core.database.model.FilterTypeEntity.Favorite
import com.example.core.database.model.FilterTypeEntity.TrendMedia
import com.example.core.database.model.FilterTypeEntity.TrendMediaByGenre
import com.example.core.domain.model.FilterType

enum class FilterTypeEntity {

    TrendMedia,
    TrendMediaByGenre,
    Favorite;

}

fun FilterType.toFilterTypeEntity() : FilterTypeEntity{
    return when(this){
        FilterType.TrendMedia -> TrendMedia
        FilterType.TrendMediaByGenre -> TrendMediaByGenre
        FilterType.Favorite -> Favorite
    }
}