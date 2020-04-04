package com.proj.newsboard.util

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter(timeForUTC0: Boolean = true) {
    private val locale = Locale.getDefault()
    private val millisecondsInDay = 60 * 60 * 24 * 1000
    private val timeZoneOffset = if (timeForUTC0) TimeZone.getDefault().rawOffset else 0

    fun getCurrentFullDate(): String {
        val date = Calendar.getInstance().time.time - timeZoneOffset

        return SimpleDateFormat("HH:mm dd-MM-yyyy", locale).format(date)
    }

    fun getCurrentDate(): String {
        val date = Calendar.getInstance().time.time - timeZoneOffset

        return SimpleDateFormat("dd-MM-yyyy", locale).format(date)
    }

    fun getCurrentTime(): String {
        val date = Calendar.getInstance().time.time - timeZoneOffset

        return SimpleDateFormat("HH:mm", locale).format(date)
    }

    fun getYesterdayFullDate(): String {
        val date = Calendar.getInstance().time.time - millisecondsInDay - timeZoneOffset

        return SimpleDateFormat("HH:mm dd-MM-yyyy", locale).format(date)
    }

    fun fromApiToNormalFormat(apiDateString: String): String {
        val apiDateStringPrepared = apiDateString.replace("T", " ").replace("Z", "")
        val apiDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale).parse(apiDateStringPrepared)

        return SimpleDateFormat("HH:mm dd-MM-yyyy", locale).format(apiDate)
    }

    fun fromNormalToApiFormat(normalDateString: String): String {
        val normalDate = SimpleDateFormat("HH:mm dd-MM-yyyy", locale).parse(normalDateString)

        return fromNormalToApiFormat(normalDate)
    }

    private fun fromNormalToApiFormat(normalDate: Date): String {
        val apiDateString = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale).format(normalDate)

        return apiDateString.replace(" ", "T").plus("Z")
    }
}