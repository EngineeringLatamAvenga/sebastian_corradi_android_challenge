package com.ualachallenge.repository

import com.ualachallenge.data.City
import com.ualachallenge.repository.data.CitiesResponse
import com.ualachallenge.repository.datasources.LocalCitiesDataSource
import com.ualachallenge.repository.datasources.RemoteCitiesDataSource

open class CitiesRepository(
    val localCitiesDataSource: LocalCitiesDataSource,
    val remoteCitiesDataSource: RemoteCitiesDataSource
) {
    suspend fun getCities(): CitiesResponse {
        val localCities = localCitiesDataSource.getCities()
        if (localCities.cities.isNotEmpty()){
            return localCities
        } else {
            val response = remoteCitiesDataSource.getCities()
            localCitiesDataSource.saveAll(response.cities)
            return response
        }
    }

    suspend fun getFavourites(): List<City> {
        return localCitiesDataSource.getFavourites()
    }

    suspend fun saveFavourite(city: City) {
        return localCitiesDataSource.saveFavourite(city)
    }

    suspend fun deleteFavourite(city: City) {
        localCitiesDataSource.deleteFavourite(city)
    }
}