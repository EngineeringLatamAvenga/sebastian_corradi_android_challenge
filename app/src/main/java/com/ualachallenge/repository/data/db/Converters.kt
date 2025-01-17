package com.ualachallenge.repository.data.db

import android.util.Log
import androidx.room.TypeConverter
import org.json.JSONObject

class Converters {
    @TypeConverter
    fun fromCoordEntityToString(coord: CoordEntity): String {
        val jsonString = "{\"lat\":\"${coord.lat}\",\"lon\":\"${coord.lon}\" }"
        Log.e("Sebas", "jsonString vale: $jsonString")
        return jsonString
    }

    @TypeConverter
    fun stringToCoordEntity(json: String): CoordEntity {
        Log.e("Sebas", "json vale: $json")
        val jsonObj = JSONObject(json)

        return CoordEntity(
            lat = jsonObj.getDouble("lat"),
            lon = jsonObj.getDouble("lon")
        )
    }
}