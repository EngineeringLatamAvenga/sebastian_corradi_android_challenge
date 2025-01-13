package com.ualachallenge.ui.cities

import android.os.Parcelable
import com.ualachallenge.data.City
import kotlinx.parcelize.Parcelize

class CitiesScreenState(
    val cities: List<City>,
    val citiesFiltered: List<City>,
    val nameFilter: String
)


sealed class CitiesScreenUiState {
    data class Loading(val data: Boolean? = null) : CitiesScreenUiState()
    data class Error(val errorModel: ResponseErrorModel) : CitiesScreenUiState()
    data class Success(val data: CitiesScreenState) : CitiesScreenUiState()
    data class Init(val data: Boolean? = null) : CitiesScreenUiState()
}

@Parcelize
data class ResponseErrorModel(
    val title: String,
    val message: String,
    val errorCode: String,
    val stackTrace: String,
) : Parcelable
