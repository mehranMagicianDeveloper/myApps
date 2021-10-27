package net.kurdsofts.weatherapplication.data.model.sealed_models

import net.kurdsofts.weatherapplication.data.model.models.Location

sealed class TimeZoneEvent(val result: Location?, val errorText: String?) {
    class Success(result: Location) : TimeZoneEvent(result, null)
    class Failure(errorText: String) : TimeZoneEvent(null, errorText)
    object Loading : TimeZoneEvent(null, null)
    object Empty : TimeZoneEvent(null, null)
}
