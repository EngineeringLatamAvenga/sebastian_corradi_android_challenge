package com.ualachallenge.repository.data.db

import androidx.room.TypeConverter
import org.json.JSONObject

class Converters {
    @TypeConverter
    fun fromCoordToString(coord: CoordEntity): String {
        val jsonString = """
    {
       "lat":"${coord.lat}",
       "lon":"${coord.lon}"
    }    
    """
        return jsonString
    }

    @TypeConverter
    fun stringToCoord(json: String): CoordEntity {
        val jsonObj = JSONObject(json)

        return CoordEntity(
            lat = jsonObj.getDouble("lat"),
            lon = jsonObj.getDouble("lon")
        )
    }
}