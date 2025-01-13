package com.ualachallenge.ui.cities

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.ualachallenge.data.City
import com.ualachallenge.data.Coord
import com.ualachallenge.ui.cities.CitiesScreenUiState.Init
import com.ualachallenge.ui.components.CityItem
import com.ualachallenge.ui.map.MapScreen

@Composable
fun CitiesScreen(viewModel: CitiesScreenViewModel = CitiesScreenViewModel()) {
    val state = viewModel.citiesScreenUiState.collectAsState()
    var currentCity by remember { mutableStateOf<City?>(null) }

    LaunchedEffect(Unit) {
        viewModel.singleEventCity.collect { city ->
            // Handle the event
            println("Event received: $city")
            currentCity = city
            //MapScreen(city)
            //TODO aca llamar a mostrar la ciudad en el mapa
        }
    }

    LaunchedEffect(Unit) {
        viewModel.citiesRequested()
    }
    when (state.value){
        is CitiesScreenUiState.Success -> {
            Log.e("-Sebas-","+++++++  successss  +++++++ mostrar todo")
            DynamicLazyColumn((state.value as CitiesScreenUiState.Success).data.cities
            ) { cityClicked ->
                //irAlMapa = true
                viewModel.cityClicked(cityClicked) }
            if (currentCity != null){
                //MapScreen(currentCity!!)
                MapScreen(City("hola", "chau", 23, Coord(-58.437089, -34.607568)))
            }
        }
        is CitiesScreenUiState.Error -> {
            Log.e("-Sebas-", "+++++++  error  +++++++ mostrar error")
            DisplayError((state.value as CitiesScreenUiState.Error).errorModel)
        }
        is Init -> Log.e("-Sebas-","+++++++  init  +++++++ no hacer nada")
        is CitiesScreenUiState.Loading -> Log.e("-Sebas-","+++++++  loading  +++++++ esperar")
    }
}

@Composable
fun DynamicLazyColumn(items: List<City>, onCityClick: (city: City) -> Unit) {
    LazyColumn {
        items(items.size) { cityIndex ->
            CityItem(items[cityIndex], onCityClick = onCityClick)
        }
    }
}

@Composable
fun DisplayError(errorModel: ResponseErrorModel) {
    val context = LocalContext.current
    val title = errorModel.errorCode
    Toast.makeText(context, "This is a toast message", Toast.LENGTH_SHORT).show()

}

@Preview
@Composable
fun CitiesScreenPreview(){
    val cities = listOf(
        City(
            country = "AR", name = "Palermo", id = 330765, coord = Coord(33.652234, 76.555435)
        ),
        City(
            country = "AR", name = "Belgrano", id = 330766, coord = Coord(33.662234, 76.6655435)
        ),
        City(
            country = "AR", name = "San telmo", id = 330767, coord = Coord(33.672234, 76.775435)
        )
    )
    DynamicLazyColumn(cities){}
}

