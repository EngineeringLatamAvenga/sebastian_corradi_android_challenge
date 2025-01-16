package com.ualachallenge.repository.data.db

import androidx.room.TypeConverter
import com.ualachallenge.data.City
import com.ualachallenge.data.Coord

class CityDBMapper {
    fun fromEntityToCity(entity: CityEntity): City {
        return City(
            name = entity.name,
            country = entity.country,
            id = entity.id,
            coord = Coord(0.0,0.0),
            favourite = entity.favourite
        )
    }

    @TypeConverter
    fun cityToEntity(city: City): CityEntity {
        return CityEntity(
            name = city.name,
            country = city.country,
            id = city.id,
            coord = "",
            favourite = city.favourite
        )
    }
}