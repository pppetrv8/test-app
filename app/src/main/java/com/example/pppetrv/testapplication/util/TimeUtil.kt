package com.example.pppetrv.testapplication.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtil {

    const val DATE_TIMESTAMP = "HH:mm:ss yyyy-MMMM-dd"
    const val SERVER_DATE_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss"
    const val DATE_FILENAME_FORMAT = "yyyyMMddHHmmSSS"
    const val DATE_SIMPLE_FILENAME_FORMAT = "dd-MMMM-yyyy HH-mm-ss"
    const val DATE_DROPBOX_FOLDER_NAME_FORMAT = "yyyy_MM_dd_HH_mm"
    private const val NANOSECONDS_VALUE = 1000000L

    private fun getSeparatedTime(timeInMillis: Long): LongArray {
        val hours = timeInMillis / TimeUnit.HOURS.toMillis(1)
        val minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1)
        val seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1)
        val millis = timeInMillis % TimeUnit.SECONDS.toMillis(1)
        val separatedTime = LongArray(4)
        separatedTime[0] = millis
        separatedTime[1] = seconds
        separatedTime[2] = minutes
        separatedTime[3] = hours
        return separatedTime
    }

    fun formatMillisHourMinSecFmt(timeInMillis: Long): String {
        val time = getSeparatedTime(timeInMillis)
        return String.format("%2d:%02d:%02d", time[3], time[2], time[1], Locale.US)
    }

    fun getCurrentTimeMillis() = (System.nanoTime().toDouble() / NANOSECONDS_VALUE.toDouble()).toLong()

    fun getFormattedDate(template: String): String = SimpleDateFormat(template,  Locale.US).format(Date())
}