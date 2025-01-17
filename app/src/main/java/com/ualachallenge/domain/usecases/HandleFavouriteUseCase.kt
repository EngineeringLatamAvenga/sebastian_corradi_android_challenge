package com.ualachallenge.domain.usecases

import com.ualachallenge.data.City
import com.ualachallenge.repository.CitiesRepository
import javax.inject.Inject

class HandleFavouriteUseCase @Inject constructor(
    protected val citiesRepository: CitiesRepository,
) {
    open suspend operator fun invoke(city: City) {
        citiesRepository.saveFavourite(city)
    }
}