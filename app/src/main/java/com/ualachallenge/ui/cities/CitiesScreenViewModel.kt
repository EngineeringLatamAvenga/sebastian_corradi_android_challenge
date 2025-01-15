package com.ualachallenge.ui.cities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ualachallenge.data.City
import com.ualachallenge.domain.usecases.GetCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CitiesScreenViewModel @Inject constructor(var getCitiesUseCase: GetCitiesUseCase) : ViewModel() {
    private val _citiesScreenUiState = MutableStateFlow<CitiesScreenUiState>(CitiesScreenUiState.Init())
    val citiesScreenUiState: StateFlow<CitiesScreenUiState> get() = _citiesScreenUiState // Expose as an immutable flow

    private val _singleEventCity = Channel<City>(Channel.BUFFERED)
    val singleEventCity = _singleEventCity.receiveAsFlow()// Expose as read-only

    private val cities: MutableList<City> = mutableListOf()

    //@Inject
    //lateinit var getCitiesUseCase: GetCitiesUseCase

    /*var getCitiesUseCase = GetCitiesUseCase(
        CitiesRepository(
            localCitiesDataSource = LocalCitiesDataSource(),
            remoteCitiesDataSource = RemoteCitiesDataSource()
        )
    )*/

    /*init {
        // This block is executed only once when the ViewModel is created
        //loadCities()
    }*/

    init {
        citiesRequested()
    }
    fun cityClicked(city: City){
        CoroutineScope(Dispatchers.Main).launch {
            _singleEventCity.send(city)
        }
    }
    fun citiesRequested(){
        viewModelScope.launch {
            val citiesResponse = getCitiesUseCase.getCities()
            cities.clear()
            cities.addAll((citiesResponse as CitiesScreenUiState.Success).data.citiesFiltered)
            _citiesScreenUiState.value = CitiesScreenUiState.Success(CitiesScreenState(cities , ""))
        }
    }

    fun filterChange(value: String) {
        Log.e("Sebas", "Filter changed to: $value")
        var oldValue = _citiesScreenUiState.value as CitiesScreenUiState.Success
        val citiesFiltered = cities.filter { it.name.contains(value, ignoreCase = true) }
        val newValue = CitiesScreenUiState.Success(CitiesScreenState(citiesFiltered, value))
        _citiesScreenUiState.value = newValue
    }

    fun favoriteClicked(id: Int, clicked: Boolean) {
        var cityIndex = cities.indexOfFirst { it.id == id }
        cities.get(cityIndex).favourite = clicked
        val filter = (_citiesScreenUiState.value as CitiesScreenUiState.Success).data.nameFilter
        val newCities = cities.filter { it.name.contains(filter, ignoreCase = true) }
        val newValue = CitiesScreenUiState.Success(CitiesScreenState(newCities, filter))
        _citiesScreenUiState.value = newValue
        Log.e("Sebas", "Fav clicked, id: $id, chnged to: $clicked")
    }
}