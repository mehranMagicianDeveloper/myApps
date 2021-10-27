package net.kurdsofts.weatherapplication.repository

import net.kurdsofts.weatherapplication.data.model.response_models.CurrentResponse
import net.kurdsofts.weatherapplication.data.model.response_models.ForecastResponse
import net.kurdsofts.weatherapplication.data.model.response_models.TimeZoneResponse
import net.kurdsofts.weatherapplication.data.model.sealed_models.Resource

interface MainRepository {

    suspend fun getTimeZone(location: String): Resource<TimeZoneResponse>
    suspend fun getForecast(location: String, daysToForecast:Int, aqi:String, alerts:String): Resource<ForecastResponse>
    suspend fun getCurrent(location: String): Resource<CurrentResponse>

}