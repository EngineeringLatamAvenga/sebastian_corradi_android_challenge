package com.ualachallenge.ui.cities

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ualachallenge.data.City
import com.ualachallenge.data.Coord
import com.ualachallenge.ui.cities.CitiesScreenUiState.Init
import com.ualachallenge.ui.components.CityItem

@Composable
fun CitiesScreen(
    modifier: Modifier = Modifier,
    viewModel: CitiesScreenViewModel = hiltViewModel(),
    navigateToMap: (city: City) -> Unit,
    navigateToInfo: () -> Unit
) {
    val state = viewModel.citiesScreenUiState.collectAsState()
    var currentCity by remember { mutableStateOf<City?>(null) }
    currentCity?.let { city ->
        navigateToMap(city)
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventCity.collect { city ->
            // Handle the event
            println("Event received: $city")
            currentCity = city
            //TODO aca llamar a mostrar la ciudad en el mapa
        }
    }

    when (state.value) {
        is CitiesScreenUiState.Success -> {
            Column(modifier = modifier) {
                SearchView(
                    viewModel = viewModel,
                    filter = (state.value as CitiesScreenUiState.Success).data.nameFilter,
                    favouritesChecked = (state.value as CitiesScreenUiState.Success).data.justFavouritesChecked,
                )
                DynamicLazyColumn((state.value as CitiesScreenUiState.Success).data.citiesFiltered,
                    { cityClicked -> viewModel.cityClicked(cityClicked) },
                    { id, clicked -> viewModel.favoriteClicked(id, clicked) },
                    navigateToInfo
                )
                /*currentCity?.let{ city ->
                    navigateToMap(city)
                }*/
            }
        }

        is CitiesScreenUiState.Error -> {
            DisplayError((state.value as CitiesScreenUiState.Error).errorModel)
        }

        is Init -> Loading()
        is CitiesScreenUiState.Loading -> Loading()
    }
}

@Composable
fun Loading(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Aligns the content to the center
    ) {
        Text(text = "Please wait, cities loading", fontSize = 24.sp)
    }
}
@Composable
fun DynamicLazyColumn(
    items: List<City>,
    onCityClick: (city: City) -> Unit,
    onItemFavoriteClicked: (id: Int, clicked: Boolean) -> Unit,
    onInfoClicked: () -> Unit
) {
    LazyColumn {
        items(items.size) { cityIndex ->
            CityItem(
                items[cityIndex],
                onCityClick = onCityClick,
                onItemFavoriteClicked = onItemFavoriteClicked,
                onInfoClicked = onInfoClicked
            )
        }
    }
}

@Composable
fun SearchView(
    viewModel: CitiesScreenViewModel,
    filter: String,
    favouritesChecked: Boolean
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        TextField(
            modifier = Modifier
                .weight(1F)
                .wrapContentHeight(),
            value = filter,
            onValueChange = { value ->
                viewModel.filterChange(value)
            }
        )
        Switch(
            checked = favouritesChecked,
            onCheckedChange = {
                viewModel.searchJustFavouritesChecked(!favouritesChecked)
            }
        )
        IconButton(
            onClick = {
                viewModel.filterChange("")
            },
            modifier = Modifier
                .padding(start = 2.dp, top = 8.dp)
                .wrapContentSize()
            // Align to top-left corner
        ) {
            Icon(
                Icons.Rounded.Close,
                contentDescription = "hola"
            )
        }
    }
}


@Composable
fun DisplayError(errorModel: ResponseErrorModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Aligns the content to the center
    ) {
        Column {
            Text(text = errorModel.title, fontSize = 24.sp)
            Text(text = errorModel.message, fontSize = 20.sp)
            Text(text = "Error code: ${errorModel.errorCode}", fontSize = 20.sp)
        }
    }

}

@Preview
@Composable
fun CitiesScreenPreview() {
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
    SearchView(viewModel = hiltViewModel(), filter = "au", false)
    DynamicLazyColumn(cities, {}, { id, clicked -> }, {})
}

