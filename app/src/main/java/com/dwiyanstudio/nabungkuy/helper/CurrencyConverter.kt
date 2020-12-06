package com.dwiyanstudio.nabungkuy.helper


import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit

object CurrencyConverter {
    @JvmStatic
    fun currencyConverter(harga: Double): String {
        val hargaIntToDouble = if (harga >= 0) harga else 0.0
        val formatRupiah =
            NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(hargaIntToDouble)
        return formatRupiah.replace("-", "")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    fun currencyPerMonth(harga: Double, time: String): String {
        val timeZone = LocalDateTime.now()
        val timeDate = SimpleDateFormat("yyyy/MM/dd").parse(time)
        val convertLocalDate = Date.from(timeZone.atZone(ZoneId.systemDefault()).toInstant())
        var month = TimeConverter.monthsBetweenDates(convertLocalDate, timeDate)
        val hasilAkhir = harga / month
        return "${currencyConverter(hasilAkhir)}/Bulan"

    }

    @JvmStatic
    fun currencyPerDay(harga: Double, time: String): String {
        val timeDate = SimpleDateFormat("yyyy/MM/dd").parse(time)
        val milisecond = timeDate.time - Calendar.getInstance().timeInMillis
        val timeUnit = TimeUnit.DAYS.convert(milisecond, TimeUnit.MILLISECONDS)
        val operation: Double
        if (harga == 0.0) {
            operation = 0.0
        } else {
            operation = harga / timeUnit

        }
        return currencyConverter(operation) + "/Hari"

    }


}