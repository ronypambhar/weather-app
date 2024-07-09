package com.app.data.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun getFormatTime(timeStamp: Long, offsetSeconds: Int): String? {
    val cal = Calendar.getInstance()
    cal.timeInMillis = timeStamp * 1000L
    cal.add(Calendar.SECOND, offsetSeconds)

    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(cal.time)
}