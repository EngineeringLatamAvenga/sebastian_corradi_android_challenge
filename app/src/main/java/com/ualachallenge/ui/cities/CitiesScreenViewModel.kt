package com.ualachallenge.ui.cities

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ualachallenge.data.City
import com.ualachallenge.domain.usecases.FilterCitiesUseCase
import com.ualachallenge.domain.usecases.GetCitiesUseCase
import com.ualachallenge.domain.usecases.GetFavouritesUseCase
import com.ualachallenge.domain.usecases.HandleFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CitiesScreenViewModel @Inject constructor(
    var getCitiesUseCase: GetCitiesUseCase,
    var filterCitiesUseCase: FilterCitiesUseCase
) : ViewModel() {
    private val _citiesScreenUiState =
        MutableStateFlow<CitiesScreenUiState>(CitiesScreenUiState.Init())
    val citiesScreenUiState: StateFlow<CitiesScreenUiState> get() = _citiesScreenUiState // Expose as an immutable flow

    private val _singleEventCity = Channel<City>(Channel.BUFFERED)
    val singleEventCity = _singleEventCity.receiveAsFlow()// Expose as read-only

    private var cities: MutableList<City> = mutableListOf()

    @Inject
    lateinit var handleFavoriteUseCase: HandleFavouriteUseCase

    @Inject
    lateinit var getFavoriteUseCase: GetFavouritesUseCase

    private val _currentCityClicked = MutableStateFlow<City?>(null)
    val currentCityClicked: StateFlow<City?> get() = _currentCityClicked // Expose as an immutable flow

    init {
        citiesRequested()
    }

    fun cityClicked(city: City) {
        CoroutineScope(Dispatchers.Main).launch {
            _singleEventCity.send(city)
        }
        _currentCityClicked.update { city }

    }

    fun citiesRequested() {
        CoroutineScope(Dispatchers.IO).launch {
            val citiesResponse = getCitiesUseCase()
            cities.clear()
            cities.addAll((citiesResponse as CitiesScreenUiState.Success).data.citiesFiltered)
            _citiesScreenUiState.value =
                CitiesScreenUiState.Success(CitiesScreenState(cities, "", false))
        }
    }

    fun filterChange(value: String) {
        Log.e("Sebas", "Filter changed to: $value")
        val checked =
            (_citiesScreenUiState.value as CitiesScreenUiState.Success).data.justFavouritesChecked
        val newCitiesScreenUIState = filterCitiesUseCase.invoke(cities, value, checked)
        _citiesScreenUiState.value = newCitiesScreenUIState
    }

    fun favoriteClicked(id: Int, clicked: Boolean) {
        var cityIndex = cities.indexOfFirst { it.id == id }
        cities.get(cityIndex).favourite = clicked
        CoroutineScope(Dispatchers.IO).launch {
            handleFavoriteUseCase(cities[cityIndex])
            Log.e("Sebas", "Fav clicked, id: $id, chnged to: $clicked")
        }
        val filter = (_citiesScreenUiState.value as CitiesScreenUiState.Success).data.nameFilter
        val justFavorites =
            (_citiesScreenUiState.value as CitiesScreenUiState.Success).data.justFavouritesChecked
        val newCities =
            (_citiesScreenUiState.value as CitiesScreenUiState.Success).data.citiesFiltered

        val newValue = CitiesScreenUiState.Success(
            CitiesScreenState(
                newCities.subList(0, newCities.lastIndex),
                filter,
                justFavorites
            )
        )
        _citiesScreenUiState.value = newValue
    }

    fun searchJustFavouritesChecked(checked: Boolean) {
        val filter = (_citiesScreenUiState.value as CitiesScreenUiState.Success).data.nameFilter
        val citiesFiltered = filterCitiesUseCase(cities, filter, checked)
        _citiesScreenUiState.value = citiesFiltered
    }
}