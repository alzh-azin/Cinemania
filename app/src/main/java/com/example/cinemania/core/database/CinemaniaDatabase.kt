package com.example.cinemania.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinemania.core.database.dao.CinemaniaDao
import com.example.cinemania.core.database.model.MediaEntity

@Database(
    entities = [
        MediaEntity::class
    ],
    version = 1
)
abstract class CinemaniaDatabase : RoomDatabase() {

    abstract fun cinemaniaDao(): CinemaniaDao
}