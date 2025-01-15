package com.ualachallenge

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ualachallenge.data.City
import com.ualachallenge.data.Coord
import com.ualachallenge.ui.cities.CitiesScreen
import com.ualachallenge.ui.cities.CitiesScreenViewModel
import com.ualachallenge.ui.map.MapScreen
import com.ualachallenge.ui.theme.UalaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            UalaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                    //CitiesScreen(viewModel = hiltViewModel<CitiesScreenViewModel>())
                }
            }
        }
    }
}

@Composable
//fun HomeScreen(onNavigate: () -> Unit) {
fun MainScreen() {
    var currentScreen:Screen by remember { mutableStateOf(Screen.CityScreen) }
    var currentCity:City? by remember { mutableStateOf(null) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var modifier:Modifier = Modifier
    if (isLandscape) {
        Log.e("Sebas", "LANDSCAPE --- LANDSCAPE --- LANDSCAPE")
        // Landscape layout: Show list and map side-by-side
        modifier = Modifier.fillMaxWidth(0.5F)
        Row(modifier = Modifier.fillMaxWidth()) {
            CitiesScreen(
                modifier = modifier,
                viewModel = hiltViewModel<CitiesScreenViewModel>(),
                navigateToMap = {
                    Log.e("Sebas", "no hace nada el click")
                }
            )
            MapScreen(
                modifier = Modifier.fillMaxWidth(),
                City("Ar", "Palermo", 23, Coord(-58.6444535, -28.44455342))
            ){Log.e("Sebas", "no hace nada el volver")}
        }
    } else {
        Log.e("Sebas", "PORTRAIT --- PORTRAIT --- PORTRAIT")
        modifier = Modifier.fillMaxWidth()
        // Portrait layout: Show only one screen at a time
        when (currentScreen) {
            Screen.CityScreen -> CitiesScreen(
                modifier = modifier,
                viewModel = hiltViewModel<CitiesScreenViewModel>(),
                navigateToMap = { selectedCity -> currentCity = selectedCity
                    currentScreen = Screen.MapScreen})
            Screen.MapScreen -> currentCity?.let { city -> MapScreen(modifier,
                                                                    city){
                Log.e("Sebas2", "desde mainActivity")
                currentScreen = Screen.CityScreen
            } }
        }
    }
    /*when (currentScreen) {
        Screen.CityScreen -> CitiesScreen(
            modifier = modifier,
            viewModel = hiltViewModel<CitiesScreenViewModel>(),
            navigateToMap = { selectedCity -> currentCity = selectedCity
                                              currentScreen = Screen.MapScreen})
        Screen.MapScreen -> currentCity?.let { city -> MapScreen(modifier, city){
            Log.e("Sebas2", "desde mainActivity")
            currentScreen = Screen.CityScreen
        } }
    }*/
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UalaTheme {
        CitiesScreen(navigateToMap = {})
    }
}

sealed class Screen {
    object CityScreen : Screen()
    object MapScreen : Screen()
}