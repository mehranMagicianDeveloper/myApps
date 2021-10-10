package net.kurdsofts.weatherapplication.data.retrofit

import net.kurdsofts.weatherapplication.data.models.TimeZoneResponse
import net.kurdsofts.weatherapplication.data.models.WeatherResponse
import net.kurdsofts.weatherapplication.util.UtilConstants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {

    @GET("/v1/timezone.json")
    suspend fun getTimeZone(
        @Query("key")
        apiKey: String = API_KEY,
        @Query("q")
        location: String = "Barcelona"
    ): Response<TimeZoneResponse>

    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key")
        apiKey: String = API_KEY,
        @Query("q")
        location: String = "Barcelona",
        @Query("aqi")
        aqi: String = "no"
    ): Response<WeatherResponse>


}