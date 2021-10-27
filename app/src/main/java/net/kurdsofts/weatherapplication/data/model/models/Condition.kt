package net.kurdsofts.weatherapplication.data.model.models

import javax.inject.Inject

data class Condition @Inject constructor(
    val code: Int,
    val icon: String,
    val text: String
)