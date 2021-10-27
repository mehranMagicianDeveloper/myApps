package net.kurdsofts.weatherapplication.data.retrofit

import net.kurdsofts.weatherapplication.data.model.response_models.CurrentResponse
import net.kurdsofts.weatherapplication.data.model.response_models.ForecastResponse
import net.kurdsofts.weatherapplication.data.model.response_models.TimeZoneResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {

    @GET("/v1/current.json")
    suspend fun getCurrent(
        @Query("key")
        apiKey: String,
        @Query("q")
        location: String,
        @Query("aqi")
        aqi: String
    ): Response<CurrentResponse>

    @GET("/v1/forecast.json")
    suspend fun getForecast(
        @Query("key")
        apiKey: String,
        @Query("q")
        location: String,
        @Query("days")
        days: Int,
        @Query("aqi")
        aqi: String,
        @Query("alerts")
        alerts: String
    ): Response<ForecastResponse>

    @GET("/v1/timezone.json")
    suspend fun getTimeZone(
        @Query("key")
        apiKey: String,
        @Query("q")
        location: String
    ): Response<TimeZoneResponse>


}