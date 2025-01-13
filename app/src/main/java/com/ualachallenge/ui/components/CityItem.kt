package com.ualachallenge.ui.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ualachallenge.R
import com.ualachallenge.data.City
import com.ualachallenge.data.Coord

@Composable
fun CityItem(city: City, onCityClick: (city: City) -> Unit,) {
    Column(modifier = Modifier
        .background(color = colorResource(id = R.color.cities_detail_background))
        .clickable {
            Log.w("CityItem", "${city.name} Clicked")
            onCityClick(city)
        }) {
        Row(
            modifier = Modifier
                .padding(horizontal = 0.dp)
                .padding(top = 5.dp)
                .fillMaxWidth()
                .background(
                    colorResource(id = R.color.cities_title_background),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .padding(horizontal = 8.dp),
                text = "${city.name}, ${city.country}",
                color = MaterialTheme.colorScheme.primary
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorResource(id = R.color.cities_detail_background)
                )
                //.padding(bottom = 0.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .padding(horizontal = 16.dp),
                text = "${city.coord.lon}, ${city.coord.lat}",
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .padding(horizontal = 12.dp),
                text = "toogle favorite",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun cityItemPreviewDay() {
    val city = City(
        country = "AR", name = "Palermo", id = 330765, coord = Coord(33.652234, 76.555435)
    )
    CityItem(city){}
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun cityItemPreviewNight() {
    val city = City(
        country = "AR", name = "Palermo", id = 330765, coord = Coord(33.652234, 76.555435)
    )
    CityItem(city){}
}