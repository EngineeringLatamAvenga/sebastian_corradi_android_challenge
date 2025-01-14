package com.ualachallenge.ui.map

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ualachallenge.data.City

@Composable
fun MapScreen(city: City, back: () -> Unit) {
    val cityCoord = LatLng(city.coord.lat, city.coord.lon)
    val pinLocation = LatLng(city.coord.lat, city.coord.lon)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityCoord, 10f)
        //position = com.google.android.gms.maps.CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
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

        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(Modifier.weight(1F))
            Button(
                onClick = {
                    Log.e("Sebas", "back click")
                    back()
                })
            {
                Text(text = "volver")
            }

        }
    }
}