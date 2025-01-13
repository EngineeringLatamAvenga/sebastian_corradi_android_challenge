package com.ualachallenge.repository.data

import com.google.gson.annotations.SerializedName
import com.ualachallenge.data.City

data class CitiesResponse(
    val cities: List<City> = listOf(),
    val success: Boolean = false,
    val errorCode: Int = 0,
    val errorMessage: String? = null,
    val errorResId: Int? = null
)

data class CityDTO(
    @SerializedName("country") val country:String,
    @SerializedName("name") val name: String,
    @SerializedName("_id") val id: Int,
    @SerializedName("coord") val coordDTO: CoordDTO
)

data class CoordDTO(
    val lon: Double,
    val lat: Double
)