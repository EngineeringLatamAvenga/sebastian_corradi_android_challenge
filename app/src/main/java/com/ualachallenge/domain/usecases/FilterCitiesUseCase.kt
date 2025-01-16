package com.ualachallenge.domain.usecases

import com.ualachallenge.data.City
import com.ualachallenge.ui.cities.CitiesScreenState
import com.ualachallenge.ui.cities.CitiesScreenUiState
import javax.inject.Inject

class FilterCitiesUseCase  @Inject constructor() {
    /*open operator  fun invoke(listToBeFiltered: List<City>, filter:String): List<City> {
        return listToBeFiltered.filter { it.name.startsWith(filter) }
    }*/

    open operator  fun invoke(listToBeFiltered: List<City>, filter:String, favouritesChecked: Boolean): CitiesScreenUiState {
        val citiesFiltered = listToBeFiltered.filter { it.name.startsWith(filter, ignoreCase = true) }.filter { it.favourite == favouritesChecked }

        var response = CitiesScreenUiState.Success(data = CitiesScreenState(
            citiesFiltered = citiesFiltered,
            nameFilter = filter,
            justFavouritesChecked = favouritesChecked
        ))
        return response
    }
}