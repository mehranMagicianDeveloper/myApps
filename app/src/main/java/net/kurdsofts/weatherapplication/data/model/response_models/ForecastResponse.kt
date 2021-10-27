package net.kurdsofts.weatherapplication.data.model.response_models

import net.kurdsofts.weatherapplication.data.model.models.Current
import net.kurdsofts.weatherapplication.data.model.models.Forecast
import net.kurdsofts.weatherapplication.data.model.models.Location
import javax.inject.Inject

data class ForecastResponse @Inject constructor(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)