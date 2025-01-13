package com.ualachallenge.repository

import com.ualachallenge.data.City

interface CitiesDataSource {
    fun getCities():List<City>
}