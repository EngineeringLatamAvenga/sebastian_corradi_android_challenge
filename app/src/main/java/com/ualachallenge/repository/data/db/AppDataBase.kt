package com.ualachallenge.repository.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ualachallenge.repository.data.db.dao.CityDao

@Database(entities = [CityEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}