package com.ualachallenge.ui.map

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.ualachallenge.R
import com.ualachallenge.data.City
import com.ualachallenge.ui.cities.CitiesScreenViewModel

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: CitiesScreenViewModel = hiltViewModel(),
    back: () -> Unit) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val currentCityState = viewModel.currentCityClicked.collectAsState()
    var currentCity by remember { mutableStateOf<City?>(null) }
    currentCityState.value?.let{ city ->
        ShowMap(city,
                modifier,
                back,
                isLandscape)
    }?:run{
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // Aligns the content to the center
        ) {
            Text(text = "Please select a city", fontSize = 24.sp)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventCity.collect { city ->
            // Handle the event
            println("Event received: $city")
            currentCity = city
        }
    }

}

@Composable
fun ShowMap(city: City,
            modifier: Modifier = Modifier,
            back: () -> Unit,
            isLandscape: Boolean
            ){
    val cityCoord = LatLng(city.coord.lat, city.coord.lon)
    val pinLocation = LatLng(city.coord.lat, city.coord.lon)
    val cameraPositionState = CameraPositionState(
        position = CameraPosition.fromLatLngZoom(cityCoord, 10f)
        //position = com.google.android.gms.maps.CameraPosition.fromLatLngZoom(singapore, 10f)
    )

    Box(
        modifier = modifier
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // Add a marker (pin) at the specified location
            Marker(
                state = MarkerState(position = pinLocation),
                title = "San Francisco",
                snippet = "This is a marker in San Francisco"
            )
        }

        if (!isLandscape) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.weight(1F))
                Button(
                    onClick = {
                        back()
                    })
                {
                    Text(text = stringResource(R.string.back))
                }

            }
        }
    }
}