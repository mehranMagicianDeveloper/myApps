package net.kurdsofts.weatherapplication.repository

import net.kurdsofts.weatherapplication.data.models.TimeZoneResponse
import net.kurdsofts.weatherapplication.data.models.WeatherResponse
import net.kurdsofts.weatherapplication.data.retrofit.DataApi
import net.kurdsofts.weatherapplication.util.Resource
import net.kurdsofts.weatherapplication.util.UtilConstants.API_KEY
import java.lang.Exception
import javax.inject.Inject

class TimeZoneRepository @Inject constructor(
    private val api: DataApi
) : MainRepository {
    override suspend fun getTimeZone(location: String): Resource<TimeZoneResponse> {
        return try {
            val response = api.getTimeZone(API_KEY, location)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (ex: Exception) {
//            Resource.Error(ex.message ?: "An error accrued")
            Resource.Error(ex.message!!)
        }
    }

    override suspend fun getWeather(location: String): Resource<WeatherResponse> {
        return try {
            val response = api.getWeather(API_KEY, location, "no")
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (ex: Exception) {
//            Resource.Error(ex.message ?: "An error accrued")
            Resource.Error(ex.message!!)
        }
    }
}