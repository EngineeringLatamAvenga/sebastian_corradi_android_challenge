package com.ualachallenge.domain.usecases

import com.ualachallenge.repository.CitiesRepository
import com.ualachallenge.repository.data.CitiesResponse
import com.ualachallenge.ui.cities.CitiesScreenState
import com.ualachallenge.ui.cities.CitiesScreenUiState
import com.ualachallenge.ui.cities.ResponseErrorModel
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    protected val citiesRepository: CitiesRepository,
) {
    open suspend operator  fun invoke(): CitiesResponse {
        val response = citiesRepository.getCities()
        return response
    }
    public suspend fun getCities(): CitiesScreenUiState {
        val citiesResponse = citiesRepository.getCities()
        if (citiesResponse.success) {
            var response = CitiesScreenUiState.Success(data = CitiesScreenState(
                citiesFiltered = citiesResponse.cities.sortedWith(compareBy({it.name}, {it.country})),
                nameFilter = ""
            ))
            return response
        } else {
            val errorResponse = CitiesScreenUiState.Error(errorModel = ResponseErrorModel("", "", "", ""))
            return errorResponse
        }
    }
}