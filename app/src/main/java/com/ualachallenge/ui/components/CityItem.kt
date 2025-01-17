package com.ualachallenge.ui.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ualachallenge.R
import com.ualachallenge.data.City
import com.ualachallenge.data.Coord

@Composable
fun CityItem(
    city: City,
    onCityClick: (city: City) -> Unit,
    onItemFavoriteClicked: (itemId: Int, selected: Boolean) -> Unit,
    onInfoClicked: () -> Unit
) {
    Column(modifier = Modifier
        .background(color = colorResource(id = R.color.cities_detail_background))
        .clickable {
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
                modifier = Modifier
                    .fillMaxWidth()
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
            Button(
                modifier = Modifier.height(35.dp)
                    .padding(0.dp)
                    .align(Alignment.CenterVertically),
                onClick = { onInfoClicked()}
            ){
                Text(
                    modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
                    text = "info screen")
            }
            Spacer(modifier = Modifier.size(5.dp))
            Switch(
                checked = city.favourite,
                onCheckedChange = {
                    onItemFavoriteClicked(city.id, !city.favourite)
                }
            )
        }
    }
}

@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun CityItemPreviewDay() {
    val city = City(
        country = "AR", name = "Palermo", id = 330765, coord = Coord(33.652234, 76.555435)
    )
    CityItem(city, {}, { id: Int, clicked: Boolean -> }, {})
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CityItemPreviewNight() {
    val city = City(
        country = "AR", name = "Palermo", id = 330765, coord = Coord(33.652234, 76.555435)
    )
    CityItem(city, {}, { id: Int, clicked: Boolean -> }, {})
}