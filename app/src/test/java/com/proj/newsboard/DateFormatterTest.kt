package com.proj.newsboard

import com.proj.newsboard.util.DateFormatter
import org.junit.Assert.assertEquals
import org.junit.Test

class DateFormatterTest {
    private val dateFormatter = DateFormatter(false)

    @Test
    fun getCurrentDateTest() {
        assertEquals("01-04-2020", dateFormatter.getCurrentDate())
    }

    @Test
    fun getCurrentTimeTest() {
        assertEquals("15:44", dateFormatter.getCurrentTime())
    }

    @Test
    fun getCurrentFullDateTest() {
        assertEquals("15:44 01-04-2020", dateFormatter.getCurrentFullDate())
    }

    @Test
    fun getYesterdayFullDateTest() {
        assertEquals("15:44 31-03-2020", dateFormatter.getYesterdayFullDate())
    }

    @Test
    fun fromApiToNormalFormatTest() {
        assertEquals("21:30 31-03-2020", dateFormatter.fromApiToNormalFormat("2020-03-31T21:30:00Z"))
    }

    @Test
    fun fromNormalToApiFormat() {
        val apiFormat = dateFormatter.fromNormalToApiFormat(dateFormatter.getCurrentFullDate())
        assertEquals(dateFormatter.getCurrentFullDate(), dateFormatter.fromApiToNormalFormat(apiFormat))
    }
}