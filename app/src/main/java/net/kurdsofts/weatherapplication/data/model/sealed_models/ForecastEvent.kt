package net.kurdsofts.weatherapplication.data.model.sealed_models

import net.kurdsofts.weatherapplication.data.model.response_models.ForecastResponse

sealed class ForecastEvent {
    class Success(val result: ForecastResponse) : ForecastEvent()
    class Failure(val errorText: String) : ForecastEvent()
    object Loading : ForecastEvent()
    object Empty : ForecastEvent()
}