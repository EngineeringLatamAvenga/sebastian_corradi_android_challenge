package com.ualachallenge.domain

import com.ualachallenge.data.City
import com.ualachallenge.data.Coord
import com.ualachallenge.repository.data.CityDTO
import com.ualachallenge.repository.data.CoordDTO

fun CityDTO.toCity(): City {
    return City(country = this.country,
                name = this.name,
                id = this.id,
                coord = this.coordDTO.toCoord()
                )
}
fun CoordDTO.toCoord(): Coord {
    return Coord(lat = this.lat, lon = this.lon
    )
}