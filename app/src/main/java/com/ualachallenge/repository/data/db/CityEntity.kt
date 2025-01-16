package com.ualachallenge.repository.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
class CityEntity (
    val country:String,
    val name: String,
    @PrimaryKey val id: Int,
    val coord:String,
    var favourite: Boolean = false
    ) {
    }
    data class CoordEntity(
        val lon: Double,
        val lat: Double
    )
