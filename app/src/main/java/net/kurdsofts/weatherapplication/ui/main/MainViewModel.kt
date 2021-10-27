package net.kurdsofts.weatherapplication.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

    private val _time = MutableStateFlow<TimeZoneEvent>(TimeZoneEvent.Empty)
    val time: StateFlow<TimeZoneEvent> = _time

    private val _weather = MutableStateFlow<ForecastEvent>(ForecastEvent.Empty)
    val weather: StateFlow<ForecastEvent> = _weather

    private val _current = MutableStateFlow<CurrentEvent>(CurrentEvent.Empty)
    val current: StateFlow<CurrentEvent> = _current

    fun getTimeZone(location: String) {
        if (location == "") {
            _time.value = TimeZoneEvent.Failure("Location Not Valid")
            return
        }
        viewModelScope.launch(dispatchers.io) {
            _time.value = TimeZoneEvent.Loading
            when (val timeZone = repository.getTimeZone(location)) {
                is Resource.Error -> _time.value = TimeZoneEvent.Failure(timeZone.message)
                is Resource.Success -> {
                    val locationData = timeZone.data.location
                    if (locationData.equals(null)) {
                        _time.value = TimeZoneEvent.Failure("Error:Location Data")
                    } else {
                        _time.value =
                            TimeZoneEvent.Success(locationData)
                    }
                }
            }
        }
    }

    fun getForecast(location: String, daysToForecast: Int, aqi: String, alerts: String) {
        if (location == "") {
            _weather.value = ForecastEvent.Failure("Location Not Valid")
            return
        }
        viewModelScope.launch(dispatchers.io) {
            _weather.value = ForecastEvent.Loading
            val forecast = repository
                .getForecast(
                    location,
                    daysToForecast,
                    aqi,
                    alerts
                )
            when (forecast) {
                is Resource.Error -> _weather.value = ForecastEvent.Failure(forecast.message)
                is Resource.Success -> {
                    val forecastData = forecast.data
                    if (forecastData.equals(null)) {
                        _weather.value = ForecastEvent.Failure("Error:Forecast Data")
                    } else {
                        _weather.value =
                            ForecastEvent.Success(forecastData)
                    }
                }
            }
        }
    }

    fun getCurrent(location: String) {
        if (location == "") {
            _current.value = CurrentEvent.Failure("Location Not Valid")
            return
        }
        viewModelScope.launch(dispatchers.io) {
            _current.value = CurrentEvent.Loading
            when (val current = repository.getCurrent(location)) {
                is Resource.Success -> {
                    val currentData = current.data
                    if (currentData.equals(null)) {
                        _current.value = CurrentEvent.Failure("Error:Current Data")
                    } else {
                        _current.value = CurrentEvent.Success(currentData)
                    }
                }
                is Resource.Error -> _current.value = CurrentEvent.Failure(current.message)
            }
        }
    }

}