package com.ualachallenge.repository

import com.ualachallenge.data.City
import com.ualachallenge.data.Coord

class LocalCitiesDataSource : CitiesDataSource {
    override fun getCities(): List<City> {
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
        return cities
    }
}

