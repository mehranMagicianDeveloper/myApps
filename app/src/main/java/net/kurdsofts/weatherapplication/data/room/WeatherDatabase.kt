package net.kurdsofts.weatherapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.kurdsofts.weatherapplication.data.model.response_models.CurrentResponse

@Database(
    entities = [CurrentResponse::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    CurrentConvertor::class,
    LocationConvertor::class
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDAO(): WeatherDAO

    companion object {
        const val DATABASE_NAME = "weather.db"
    }
}