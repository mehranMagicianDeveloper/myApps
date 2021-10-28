package net.kurdsofts.weatherapplication.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.kurdsofts.weatherapplication.data.model.response_models.CurrentResponse
import net.kurdsofts.weatherapplication.data.model.sealed_models.*
import net.kurdsofts.weatherapplication.repository.MainRepository
import net.kurdsofts.weatherapplication.util.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {


    // Get Current Data ----------------------------------------------------------------------------

    /*
    Initialize current data state flow
     */
    private val _current = MutableStateFlow<CurrentEvent>(CurrentEvent.Empty)
    val current: StateFlow<CurrentEvent> = _current

    /*
    Get current data using retrofit
     */

    fun getCurrentFromRetrofit(location: String) {
        if (location == "") {
            _current.value = CurrentEvent.Failure("Location Not Valid")
            return
        }
        viewModelScope.launch(dispatchers.io) {
            _current.value = CurrentEvent.Loading
            when (val current = repository.getCurrentRetrofit(location)) {
                is Resource.Success -> {
                    val currentData = current.data
                    if (currentData.equals(null)) {
                        _current.value = CurrentEvent.Failure("Error:Current Data")
                    } else {
                        insertCurrentDataToRoom(currentData)
                        _current.value = CurrentEvent.Success(currentData)
                    }
                }
                is Resource.Error -> _current.value = CurrentEvent.Failure(current.message)
            }
        }
    }

    /*
    Insert current data to room database to get it back later
     */
    private fun insertCurrentDataToRoom(currentResponse: CurrentResponse) {
        viewModelScope.launch(dispatchers.io) {
            val lastCurrentResponse = repository.getCurrentResponseFromRoom()
            if (lastCurrentResponse.equals(null)) {
                repository.insertCurrentResponseToRoom(currentResponse)
            } else {
                repository.deleteCurrentResponseFromRoom(lastCurrentResponse)
                repository.insertCurrentResponseToRoom(currentResponse)
            }
        }
    }

    /*
    Get current data using room database
     */
    fun getCurentFromRoom() {
        viewModelScope.launch(dispatchers.io) {
            val currentResponse = repository.getCurrentResponseFromRoom()
            _current.value = CurrentEvent.Success(currentResponse)
        }
    }


    // Get Forecast Data ---------------------------------------------------------------------------

    /*
    Initialize forecast data state flow
     */
    private val _forecast = MutableStateFlow<ForecastEvent>(ForecastEvent.Empty)
    val forecast: StateFlow<ForecastEvent> = _forecast

    fun getForecastFromRetrofit(
        location: String,
        daysToForecast: Int,
        aqi: String,
        alerts: String
    ) {
        if (location == "") {
            _forecast.value = ForecastEvent.Failure("Location Not Valid")
            return
        }
        viewModelScope.launch(dispatchers.io) {
            _forecast.value = ForecastEvent.Loading
            val forecast = repository
                .getForecastFromRetrofit(
                    location,
                    daysToForecast,
                    aqi,
                    alerts
                )
            when (forecast) {
                is Resource.Error -> _forecast.value = ForecastEvent.Failure(forecast.message)
                is Resource.Success -> {
                    val forecastData = forecast.data
                    if (forecastData.equals(null)) {
                        _forecast.value = ForecastEvent.Failure("Error:Forecast Data")
                    } else {
                        _forecast.value =
                            ForecastEvent.Success(forecastData)
                    }
                }
            }
        }
    }

}