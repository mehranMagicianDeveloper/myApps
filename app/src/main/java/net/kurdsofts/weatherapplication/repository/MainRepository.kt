package net.kurdsofts.weatherapplication.repository

import net.kurdsofts.weatherapplication.data.models.TimeZoneResponse
import net.kurdsofts.weatherapplication.data.models.WeatherResponse
import net.kurdsofts.weatherapplication.util.Resource

interface MainRepository {

    suspend fun getTimeZone(location: String): Resource<TimeZoneResponse>
    suspend fun getWeather(location: String): Resource<WeatherResponse>

}