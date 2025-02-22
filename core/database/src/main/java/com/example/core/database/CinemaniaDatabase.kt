package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.database.dao.CinemaniaDao
import com.example.core.database.model.GenreTypeConvertor
import com.example.core.database.model.MediaEntity
import com.example.core.database.model.MediaFilterEntity

@Database(
    entities = [
        MediaEntity::class,
        MediaFilterEntity::class
    ],
    version = 1
)
@TypeConverters(GenreTypeConvertor::class)
abstract class CinemaniaDatabase : RoomDatabase() {

    abstract fun cinemaniaDao(): CinemaniaDao
}