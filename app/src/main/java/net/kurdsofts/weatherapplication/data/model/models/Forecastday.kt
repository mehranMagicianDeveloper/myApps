package net.kurdsofts.weatherapplication.data.model.models

import javax.inject.Inject

data class Forecastday @Inject constructor(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
)