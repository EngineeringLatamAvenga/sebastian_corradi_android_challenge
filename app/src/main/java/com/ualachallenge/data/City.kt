package com.ualachallenge.data

import com.google.gson.annotations.SerializedName

data class City(
    val country:String,
    val name: String,
    val id: Int,
    val coord:Coord,
    val favourite: Boolean = false
) {
}

data class Coord(
    val lon: Double,
    val lat: Double
)