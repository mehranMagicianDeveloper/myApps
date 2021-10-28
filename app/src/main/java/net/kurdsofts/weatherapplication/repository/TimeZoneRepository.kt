package net.kurdsofts.weatherapplication.repository

import net.kurdsofts.weatherapplication.data.model.models.Current
import net.kurdsofts.weatherapplication.data.model.response_models.CurrentResponse
import net.kurdsofts.weatherapplication.data.model.response_models.ForecastResponse
import net.kurdsofts.weatherapplication.data.model.response_models.TimeZoneResponse
import net.kurdsofts.weatherapplication.data.retrofit.DataApi
import net.kurdsofts.weatherapplication.data.model.sealed_models.Resource
import net.kurdsofts.weatherapplication.data.room.WeatherDAO
import net.kurdsofts.weatherapplication.util.UtilConstants.API_KEY
import java.lang.Exception
import javax.inject.Inject

class TimeZoneRepository @Inject constructor(
    private val api: DataApi,
    private val weatherDAO: WeatherDAO
) : MainRepository {

    /*
    Retrofit Functions
    This functions is for accessing to DataApi
     */

    override suspend fun getTimeZoneFromRetrofit(location: String): Resource<TimeZoneResponse> {
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

    override suspend fun getForecastFromRetrofit(
        location: String,
        daysToForecast: Int,
        aqi: String,
        alerts: String
    ): Resource<ForecastResponse> {
        return try {
            val response = api.getForecast(API_KEY, location, daysToForecast, aqi, alerts)
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

    override suspend fun getCurrentRetrofit(location: String): Resource<CurrentResponse> {
        return try {
            val response = api.getCurrent(API_KEY, location, "no")
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

    /*
    Room functions
    This functions is for access to the WeatherDAO
     */

    override suspend fun insertCurrentResponseToRoom(currentResponse: CurrentResponse) {
        weatherDAO.insertCurrentResponse(currentResponse)
    }

    override suspend fun getCurrentResponseFromRoom(): CurrentResponse {
        return weatherDAO.getCurrentResponse()
    }

    override suspend fun deleteCurrentResponseFromRoom(currentResponse: CurrentResponse) {
        weatherDAO.deleteCurrentResponse(currentResponse)
    }

}
