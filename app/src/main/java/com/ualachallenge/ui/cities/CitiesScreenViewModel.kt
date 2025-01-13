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
class CitiesScreenViewModel @Inject constructor() : ViewModel() {
    private val _citiesScreenUiState = MutableStateFlow<CitiesScreenUiState>(CitiesScreenUiState.Init())
    val citiesScreenUiState: StateFlow<CitiesScreenUiState> get() = _citiesScreenUiState // Expose as an immutable flow

    private val _singleEventCity = Channel<City>(Channel.BUFFERED)
    val singleEventCity = _singleEventCity.receiveAsFlow()// Expose as read-only

    @Inject
    lateinit var getCitiesUseCase: GetCitiesUseCase

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
    fun cityClicked(city: City){
        CoroutineScope(Dispatchers.Main).launch {
            _singleEventCity.send(city)
        }
    }
    fun citiesRequested(){
        viewModelScope.launch {
            val citiesResponse = getCitiesUseCase.getCities()
            Log.e("Sebas", "zzzzzzzz<<<<zzzzzzzz.  response: ${citiesResponse}")
            val cities = (citiesResponse as CitiesScreenUiState.Success).data.cities
            _citiesScreenUiState.value = CitiesScreenUiState.Success(CitiesScreenState(cities , cities, ""))
        }
    }
}