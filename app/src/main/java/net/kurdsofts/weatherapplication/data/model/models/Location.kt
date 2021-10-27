package net.kurdsofts.weatherapplication.data.model.models

import javax.inject.Inject

data class Location @Inject constructor(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)