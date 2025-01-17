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
import com.ualachallenge.ui.info.InfoScreen
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
fun MainScreen() {
    var currentScreen:Screen by remember { mutableStateOf(Screen.CityScreen) }
    var currentCity:City? by remember { mutableStateOf(null) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var modifier:Modifier = Modifier
    if (isLandscape) {
        // Landscape layout: Show list and map side-by-side
        modifier = Modifier.fillMaxWidth(0.5F)
        when (currentScreen) {
            Screen.Info -> InfoScreen() {
                currentScreen = Screen.CityScreen
            }

            else -> {
                Row(modifier = Modifier.fillMaxWidth()) {
                    CitiesScreen(
                        modifier = modifier,
                        viewModel = hiltViewModel<CitiesScreenViewModel>(),
                        navigateToMap = {},
                        navigateToInfo = {currentScreen = Screen.Info}
                    )
                    MapScreen(
                        modifier = Modifier.fillMaxWidth(),
                        viewModel = hiltViewModel<CitiesScreenViewModel>(),
                        City("Ar", "Palermo", 23, Coord(-58.6444535, -28.44455342))
                    ) {}
                }
            }
        }
    } else {
        modifier = Modifier.fillMaxWidth()
        // Portrait layout: Show only one screen at a time
        when (currentScreen) {
            Screen.CityScreen -> CitiesScreen(
                modifier = modifier,
                viewModel = hiltViewModel<CitiesScreenViewModel>(),
                navigateToMap = { selectedCity -> currentCity = selectedCity
                    currentScreen = Screen.MapScreen},
                navigateToInfo = {currentScreen = Screen.Info})
            Screen.MapScreen -> currentCity?.let {
                city -> MapScreen(
                    modifier,
                    viewModel = hiltViewModel<CitiesScreenViewModel>(),
                    city) {
                        currentScreen = Screen.CityScreen
                    }
            }
            Screen.Info -> InfoScreen(){
                currentScreen = Screen.CityScreen
            }
        }
    }
}

sealed class Screen {
    object CityScreen : Screen()
    object MapScreen : Screen()
    object Info : Screen()
}