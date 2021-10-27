package net.kurdsofts.weatherapplication.data.model.sealed_models

import net.kurdsofts.weatherapplication.data.model.response_models.CurrentResponse

sealed class CurrentEvent{
    class Success(val result: CurrentResponse) : CurrentEvent()
    class Failure(val errorText: String) : CurrentEvent()
    object Loading : CurrentEvent()
    object Empty : CurrentEvent()
}
