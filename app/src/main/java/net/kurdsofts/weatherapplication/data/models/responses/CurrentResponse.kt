package net.kurdsofts.weatherapplication.data.models.responses

import net.kurdsofts.weatherapplication.data.models.Current
import net.kurdsofts.weatherapplication.data.models.Location

data class CurrentResponse(
    val location: Location,
    val current: Current
)