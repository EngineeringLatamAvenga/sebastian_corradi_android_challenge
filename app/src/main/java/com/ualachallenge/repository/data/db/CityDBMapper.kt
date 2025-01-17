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
            coord = entityToCoord(entity.coord),
            favourite = entity.favourite
        )
    }

    @TypeConverter
    fun fromCityToEntity(city: City): CityEntity {
        return CityEntity(
            name = city.name,
            country = city.country,
            id = city.id,
            coord = coordToEntity(city.coord),
            favourite = city.favourite
        )
    }

    fun coordToEntity(coord: Coord):CoordEntity{
        return CoordEntity(lat = coord.lat, lon = coord.lon)
    }

    fun entityToCoord(coordEntity: CoordEntity): Coord {
        return Coord(lat = coordEntity.lat, lon = coordEntity.lon)
    }
}