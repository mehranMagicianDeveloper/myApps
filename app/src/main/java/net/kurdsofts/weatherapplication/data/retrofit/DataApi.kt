package net.kurdsofts.weatherapplication.data.retrofit

import net.kurdsofts.weatherapplication.data.models.responses.CurrentResponse
import net.kurdsofts.weatherapplication.data.models.responses.TimeZoneResponse
import net.kurdsofts.weatherapplication.data.models.responses.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {

    companion object {
        private const val _URL = "/v1/timezone.json"
    }

    @GET(_URL)
    suspend fun getCurrent(
        @Query("key")
        apiKey:String,
        @Query("q")
        location: String,
        @Query("aqi")
        aqi: String
    ) : Response<CurrentResponse>

    @GET(_URL)
    suspend fun getTimeZone(
        @Query("key")
        apiKey: String,
        @Query("q")
        location: String
    ): Response<TimeZoneResponse>

    @GET(_URL)
    suspend fun getWeather(
        @Query("key")
        apiKey: String,
        @Query("q")
        location: String,
        @Query("aqi")
        aqi: String
    ): Response<WeatherResponse>


}