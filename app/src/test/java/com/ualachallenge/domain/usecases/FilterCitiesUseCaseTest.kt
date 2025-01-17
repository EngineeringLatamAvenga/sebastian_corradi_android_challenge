package com.ualachallenge.domain.usecases

import com.ualachallenge.data.City
import com.ualachallenge.data.Coord
import com.ualachallenge.ui.cities.CitiesScreenUiState
import org.junit.Assert
import org.junit.Test

class FilterCitiesUseCaseTest {

    @Test
    fun testFilterForA() {
        val filterCitiesUseCase = FilterCitiesUseCase()
        val cities = getCities()

        val citiesScreenUiState = filterCitiesUseCase(cities, "a", false)
        val newCities = (citiesScreenUiState as CitiesScreenUiState.Success).data.citiesFiltered

        Assert.assertEquals(cities.size - 1, newCities.size)
        Assert.assertFalse(newCities.contains(cities[4]))
        Assert.assertTrue(newCities.contains(cities[0]))
        Assert.assertTrue(newCities.contains(cities[1]))
        Assert.assertTrue(newCities.contains(cities[2]))
        Assert.assertTrue(newCities.contains(cities[3]))


    }

    @Test
    fun testFilterForAl() {
        val filterCitiesUseCase = FilterCitiesUseCase()
        val cities = getCities()

        val citiesScreenUiState = filterCitiesUseCase(cities, "al", false)
        val newCities = (citiesScreenUiState as CitiesScreenUiState.Success).data.citiesFiltered

        Assert.assertEquals(2, newCities.size)
        Assert.assertTrue(newCities.contains(cities[0]))
        Assert.assertTrue(newCities.contains(cities[1]))
    }

    @Test
    fun testFilterForAlb() {
        val filterCitiesUseCase = FilterCitiesUseCase()
        val cities = getCities()

        val citiesScreenUiState = filterCitiesUseCase(cities, "alb", false)
        val newCities = (citiesScreenUiState as CitiesScreenUiState.Success).data.citiesFiltered

        Assert.assertEquals(1, newCities.size)
        Assert.assertTrue(newCities.contains(cities[1]))
    }

    @Test
    fun testFilterForInvalidInput() {
        val filterCitiesUseCase = FilterCitiesUseCase()
        val cities = getCities()

        val citiesScreenUiState = filterCitiesUseCase(cities, "?*", false)
        val newCities = (citiesScreenUiState as CitiesScreenUiState.Success).data.citiesFiltered

        Assert.assertEquals(0, newCities.size)
    }

    fun getCities(): List<City> {
        val cities = listOf(
            City(
                country = "US", name = "Alabama", id = 330765, coord = Coord(33.652234, 76.555435)
            ),
            City(
                country = "US",
                name = "Albuquerque",
                id = 330766,
                coord = Coord(33.662234, 76.6655435)
            ),
            City(
                country = "Us", name = "Anaheim", id = 330767, coord = Coord(33.672234, 76.775435)
            ),
            City(
                country = "Us", name = "Arizona", id = 330767, coord = Coord(33.672234, 76.775435)
            ),
            City(
                country = "AU", name = "Sidney", id = 330767, coord = Coord(33.672234, 76.775435)
            )
        )
        return cities
    }
}