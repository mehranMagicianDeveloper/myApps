package net.kurdsofts.weatherapplication.data.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import net.kurdsofts.weatherapplication.data.model.models.Location

@ProvidedTypeConverter
class LocationConvertor {
    @TypeConverter
    fun fromLocation(location: Location): String {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun jsonToLocation(locationAsString: String): Location {
        return Gson().fromJson(locationAsString, Location::class.java)
    }
}