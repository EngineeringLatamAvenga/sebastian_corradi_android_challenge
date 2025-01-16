package com.ualachallenge.repository.datasources

import com.ualachallenge.data.City
import com.ualachallenge.repository.data.CitiesResponse

interface CitiesDataSource {
    suspend fun getCities(): CitiesResponse
    suspend fun getFavourites(): List<City>
    suspend fun saveFavourite(city: City)
    suspend fun deleteFavourite(city: City)
}