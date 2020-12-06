package com.dwiyanstudio.nabungkuy.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object TimeConverter {
    fun convertTimeToLong(time: String): Long {
        val timeString = SimpleDateFormat("yyyy/MM/dd").parse(time)
        return timeString.time
    }


    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.O)
    fun toMonthRemaining(time: String): String {
        val timeZone = LocalDateTime.now()
        val timeDate = SimpleDateFormat("yyyy/MM/dd").parse(time)
        val convertLocalDate = Date.from(timeZone.atZone(ZoneId.systemDefault()).toInstant())

        return "${monthsBetweenDates(convertLocalDate, timeDate)} Bulan"
    }

    fun monthsBetweenDates(startDate: Date?, endDate: Date?): Int {
        val start = Calendar.getInstance()
        start.time = startDate
        val end = Calendar.getInstance()
        end.time = endDate
        var monthsBetween = 0
        var dateDiff = end[Calendar.DAY_OF_MONTH] - start[Calendar.DAY_OF_MONTH]
        if (dateDiff < 0) {
            val borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH)
            dateDiff = end[Calendar.DAY_OF_MONTH] + borrrow - start[Calendar.DAY_OF_MONTH]
            monthsBetween--
            if (dateDiff > 0) {
                monthsBetween++
            }
        } else {
            monthsBetween++
        }
        monthsBetween += end[Calendar.MONTH] - start[Calendar.MONTH]
        monthsBetween += (end[Calendar.YEAR] - start[Calendar.YEAR]) * 12
        return monthsBetween
    }

}