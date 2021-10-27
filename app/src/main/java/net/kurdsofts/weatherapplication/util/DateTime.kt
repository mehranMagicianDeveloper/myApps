package net.kurdsofts.weatherapplication.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateTime {

    @SuppressLint("SimpleDateFormat")
    private fun getDayOfWeek(): String {
        val sdf = SimpleDateFormat("EEE")
        val d = Date()
        return sdf.format(d)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getMonth(): String {
        val cal = Calendar.getInstance()
        val monthDate = SimpleDateFormat("MMMM")
        return monthDate.format(cal.time)
    }

    private fun getDayOfMonth(): String {
        val cal = Calendar.getInstance()
        val dayOfMonth = cal[Calendar.DAY_OF_MONTH]
        return dayOfMonth.toString()
    }

    private fun getCurrentTime(): String {
        val cal = Calendar.getInstance()
        val hours = cal[Calendar.HOUR_OF_DAY]
        val minutes = cal[Calendar.MINUTE]
        var minutesToString = if (minutes < 10) {
            "0$minutes"
        } else {
            "$minutes"
        }
        return "$hours:$minutesToString"
    }

    fun getDatAndTime(): String {

        return "${getDayOfWeek()}, " +
                "${getMonth()} " +
                "${getDayOfMonth()} " +
                "${getCurrentTime()} "

    }

}