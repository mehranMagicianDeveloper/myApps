package net.kurdsofts.weatherapplication.repository

import net.kurdsofts.weatherapplication.data.models.responses.CurrentResponse
import net.kurdsofts.weatherapplication.data.models.responses.TimeZoneResponse
import net.kurdsofts.weatherapplication.data.models.responses.WeatherResponse
import net.kurdsofts.weatherapplication.util.Resource

interface MainRepository {

    suspend fun getTimeZone(location: String): Resource<TimeZoneResponse>
    suspend fun getWeather(location: String): Resource<WeatherResponse>
    suspend fun getCurrent(location: String): Resource<CurrentResponse>

}