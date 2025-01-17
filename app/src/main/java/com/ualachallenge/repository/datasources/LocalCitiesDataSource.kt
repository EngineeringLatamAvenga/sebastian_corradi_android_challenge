package com.ualachallenge.repository.datasources

import android.util.Log
import com.ualachallenge.data.City
import com.ualachallenge.data.Coord
import com.ualachallenge.domain.toCity
import com.ualachallenge.repository.data.CitiesResponse
import com.ualachallenge.repository.data.db.CityDBMapper
import com.ualachallenge.repository.data.db.dao.CityDao
import javax.inject.Inject

class LocalCitiesDataSource @Inject constructor(
    private val cityDao: CityDao
) : CitiesDataSource {
    override suspend fun getCities(): CitiesResponse {
        val cities = listOf(
            City(
                country = "AR", name = "Palermo", id = 330765, coord = Coord(33.652234, 76.555435)
            ),
            City(
                country = "AR", name = "Belgrano", id = 330766, coord = Coord(33.662234, 76.6655435)
            ),
            City(
                country = "AR", name = "San telmo", id = 330767, coord = Coord(33.672234, 76.775435)
            )
        )
        return CitiesResponse(success = true, cities = cities)
    }

    override suspend fun getFavourites(): List<City> {
        val citiesEntity = cityDao.getAll()
        Log.e("Sebas", "cities: $citiesEntity")
        return citiesEntity.map { CityDBMapper().fromEntityToCity(it) }
    }

    override suspend fun saveFavourite(city: City) {
        val cityEntity = CityDBMapper().cityToEntity(city)
        return cityDao.insertAll(cityEntity)
    }

    override suspend fun deleteFavourite(city: City) {
        cityDao.delete(CityDBMapper().cityToEntity(city))
    }
}

