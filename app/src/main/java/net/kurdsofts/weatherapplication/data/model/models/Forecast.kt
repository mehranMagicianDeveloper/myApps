package net.kurdsofts.weatherapplication.data.model.models

import javax.inject.Inject

data class Forecast @Inject constructor(
    val forecastday: List<Forecastday>
)