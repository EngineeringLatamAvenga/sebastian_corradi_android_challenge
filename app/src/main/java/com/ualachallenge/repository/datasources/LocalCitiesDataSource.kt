package com.ualachallenge.repository.datasources

import android.util.Log
import com.ualachallenge.data.City
import com.ualachallenge.repository.data.CitiesResponse
import com.ualachallenge.repository.data.db.CityDBMapper
import com.ualachallenge.repository.data.db.dao.CityDao
import javax.inject.Inject

class LocalCitiesDataSource @Inject constructor(
    private val cityDao: CityDao
) : CitiesDataSource {
    override suspend fun getCities(): CitiesResponse {
        val citiesEntity = cityDao.getAll()
        return CitiesResponse(success = true, cities = citiesEntity.map { CityDBMapper().fromEntityToCity(it) })
    }

    override suspend fun getFavourites(): List<City> {
        val citiesEntity = cityDao.getAll()
        return citiesEntity.map { CityDBMapper().fromEntityToCity(it) }
    }

    override suspend fun saveFavourite(city: City) {
        val cityEntity = CityDBMapper().fromCityToEntity(city)
        return cityDao.insertAll(cityEntity)
    }

    override suspend fun deleteFavourite(city: City) {
        cityDao.delete(CityDBMapper().fromCityToEntity(city))
    }

    suspend fun saveAll(cities: List<City>) {
        val dbCities = cities.map { CityDBMapper().fromCityToEntity(it) }
        return cityDao.insertCityList(dbCities)
    }
}

