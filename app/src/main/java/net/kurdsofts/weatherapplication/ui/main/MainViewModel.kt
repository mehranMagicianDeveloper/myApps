package net.kurdsofts.weatherapplication.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.kurdsofts.weatherapplication.data.models.Location
import net.kurdsofts.weatherapplication.data.models.responses.CurrentResponse
import net.kurdsofts.weatherapplication.data.models.responses.WeatherResponse
import net.kurdsofts.weatherapplication.repository.MainRepository
import net.kurdsofts.weatherapplication.util.DispatcherProvider
import net.kurdsofts.weatherapplication.util.Resource
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class TimeZoneEvent {
        class Success(val result: Location) : TimeZoneEvent()
        class Failure(val errorText: String) : TimeZoneEvent()
        object Loading : TimeZoneEvent()
        object Empty : TimeZoneEvent()
    }

    sealed class WeatherEvent {
        class Success(val result: WeatherResponse) : WeatherEvent()
        class Failure(val errorText: String) : WeatherEvent()
        object Loading : WeatherEvent()
        object Empty : WeatherEvent()
    }

    sealed class CurrentEvent {
        class Success(val result: CurrentResponse) : CurrentEvent()
        class Failure(val errorText: String) : CurrentEvent()
        object Loading : CurrentEvent()
        object Empty : CurrentEvent()
    }

    private val _time = MutableStateFlow<TimeZoneEvent>(TimeZoneEvent.Empty)
    val time: StateFlow<TimeZoneEvent> = _time

    private val _weather = MutableStateFlow<WeatherEvent>(WeatherEvent.Empty)
    val weather: StateFlow<WeatherEvent> = _weather

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
                is Resource.Error -> _time.value = TimeZoneEvent.Failure(timeZone.message!!)
                is Resource.Success -> {
                    val locationData = timeZone.data!!.location
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

    fun getWeather(location: String) {
        if (location == "") {
            _weather.value = WeatherEvent.Failure("Location Not Valid")
            return
        }
        viewModelScope.launch(dispatchers.io) {
            _weather.value = WeatherEvent.Loading
            when (val weather = repository.getWeather(location)) {
                is Resource.Error -> _weather.value = WeatherEvent.Failure(weather.message!!)
                is Resource.Success -> {
                    val weatherData = weather.data!!
                    if (weatherData.equals(null)) {
                        _weather.value = WeatherEvent.Failure("Error:Weather Data")
                    } else {
                        _weather.value =
                            WeatherEvent.Success(weatherData)
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
                    val currentData = current.data!!
                    if (currentData.equals(null)) {
                        _current.value = CurrentEvent.Failure("Error:Current Data")
                    } else {
                        _current.value = CurrentEvent.Success(currentData)
                    }
                }
                is Resource.Error -> _current.value = CurrentEvent.Failure(current.message!!)
            }
        }
    }

}