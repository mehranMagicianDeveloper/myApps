package net.kurdsofts.weatherapplication.repository

import net.kurdsofts.weatherapplication.data.model.models.Current
import net.kurdsofts.weatherapplication.data.model.response_models.CurrentResponse
import net.kurdsofts.weatherapplication.data.model.response_models.ForecastResponse
import net.kurdsofts.weatherapplication.data.model.response_models.TimeZoneResponse
import net.kurdsofts.weatherapplication.data.model.sealed_models.Resource

interface MainRepository {

    // Retrofit functions
    suspend fun getTimeZoneFromRetrofit(location: String): Resource<TimeZoneResponse>
    suspend fun getForecastFromRetrofit(
        location: String,
        daysToForecast: Int,
        aqi: String,
        alerts: String
    ): Resource<ForecastResponse>

    suspend fun getCurrentRetrofit(location: String): Resource<CurrentResponse>

    //Room Functions
    suspend fun insertCurrentResponseToRoom(currentResponse: CurrentResponse)
    suspend fun getCurrentResponseFromRoom(): CurrentResponse
    suspend fun deleteCurrentResponseFromRoom(currentResponse: CurrentResponse)

}