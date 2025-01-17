package com.ualachallenge.repository.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ualachallenge.data.City
import com.ualachallenge.repository.data.db.CityEntity

@Dao
interface CityDao {
    @Query("SELECT * FROM cities")
    fun getAll(): List<CityEntity>

    @Query("SELECT * FROM cities WHERE id IN (:citiesId)")
    fun loadAllByIds(citiesId: IntArray): List<CityEntity>

    @Query("SELECT * FROM cities WHERE id = :cityId LIMIT 1")
    fun findById(cityId: Int): CityEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg cities: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Replace if conflicts occur
    suspend fun insertCityList(items: List<CityEntity>)

    @Delete
    fun delete(city: CityEntity)
}