package net.kurdsofts.weatherapplication.data.model.response_models

import net.kurdsofts.weatherapplication.data.model.models.Location
import javax.inject.Inject


data class TimeZoneResponse @Inject constructor(
    val location: Location
)