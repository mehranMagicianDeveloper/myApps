package net.kurdsofts.weatherapplication.data.model.models

import javax.inject.Inject

data class Astro @Inject constructor(
    val moon_illumination: String,
    val moon_phase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)