package com.ualachallenge.repository.data.db

import android.util.Log
import androidx.room.TypeConverter
import org.json.JSONObject

class Converters {
    @TypeConverter
    fun fromCoordEntityToString(coord: CoordEntity): String {
        val jsonString = "{\"lat\":\"${coord.lat}\",\"lon\":\"${coord.lon}\" }"
        return jsonString
    }

    @TypeConverter
    fun stringToCoordEntity(json: String): CoordEntity {
        val jsonObj = JSONObject(json)

        return CoordEntity(
            lat = jsonObj.getDouble("lat"),
            lon = jsonObj.getDouble("lon")
        )
    }
}