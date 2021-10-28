package net.kurdsofts.weatherapplication.data.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import net.kurdsofts.weatherapplication.data.model.models.Current

@ProvidedTypeConverter
class CurrentConvertor {
    @TypeConverter
    fun fromCurrent(current: Current): String {
        return Gson().toJson(current)
    }

    @TypeConverter
    fun jsonToCurrent(currentAsString: String): Current {
        return Gson().fromJson(currentAsString, Current::class.java)
    }
}