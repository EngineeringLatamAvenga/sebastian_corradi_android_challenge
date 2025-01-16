package com.ualachallenge.repository.datasources

import com.ualachallenge.R
import com.ualachallenge.data.City
import com.ualachallenge.data.Coord
import com.ualachallenge.domain.toCity
import com.ualachallenge.repository.api.ApiClient
import com.ualachallenge.repository.data.CitiesResponse
import retrofit2.HttpException
import java.net.SocketTimeoutException

class RemoteCitiesDataSource: CitiesDataSource {
    override suspend fun getCities(): CitiesResponse {
        try {
            val citiesDTO = ApiClient.apiService.getCities()
            citiesDTO?.let {
                return CitiesResponse(success = true, cities = citiesDTO.map { it.toCity() })
            }?: run {
                return CitiesResponse(success = false, errorResId = R.string.no_cities_available)
            }
        } catch (e: HttpException) {
            return CitiesResponse(errorCode = e.code(),
                errorMessage = e.message,
                errorResId = R.string.error_on_server_call,
                success = false)
        } catch (e: SocketTimeoutException) {
            return CitiesResponse(
                errorCode = 400,
                errorMessage = e.message,
                errorResId = R.string.error_timeout,
                success = false
            )
        }
    }

    override suspend fun getFavourites(): List<City> {
        TODO("Not yet implemented")
    }

    override suspend fun saveFavourite(city: City) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavourite(city: City) {
        TODO("Not yet implemented")
    }

}