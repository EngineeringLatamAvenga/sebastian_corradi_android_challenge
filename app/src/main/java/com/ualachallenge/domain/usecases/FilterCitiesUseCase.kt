package com.ualachallenge.domain.usecases

import com.ualachallenge.data.City
import com.ualachallenge.repository.CitiesRepository
import com.ualachallenge.repository.data.CitiesResponse
import com.ualachallenge.ui.cities.CitiesScreenState
import com.ualachallenge.ui.cities.CitiesScreenUiState
import com.ualachallenge.ui.cities.ResponseErrorModel
import javax.inject.Inject

class FilterCitiesUseCase  @Inject constructor() {
    /*open operator  fun invoke(listToBeFiltered: List<City>, filter:String): List<City> {
        return listToBeFiltered.filter { it.name.startsWith(filter) }
    }*/

    open operator  fun invoke(listToBeFiltered: List<City>, filter:String): CitiesScreenUiState {
        val citiesFiltered = listToBeFiltered.filter { it.name.startsWith(filter, ignoreCase = true) }
        var response = CitiesScreenUiState.Success(data = CitiesScreenState(
            citiesFiltered = citiesFiltered,
            nameFilter = filter
        ))
        return response
    }
}