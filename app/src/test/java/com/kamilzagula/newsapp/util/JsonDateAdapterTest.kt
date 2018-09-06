package com.kamilzagula.newsapp.util

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.*

class JsonDateAdapterTest {

    private lateinit var adapter: JsonDateAdapter

    @Before
    fun setUp() {
        adapter = JsonDateAdapter()
    }

    @Test
    fun test_shouldParseDateString() {
        val expected = getDate(2018, 7, 29, 11, 0, 0)
        val dateString = "2018-08-29T11:00:00Z"
        val result = adapter.dateToJson(dateString)
        assertEquals(expected.time, result.time)
    }

    @Test
    fun test_shouldNotParseDateString() {
        val expected = getDate(2018, 7, 29, 11, 0, 0)
        val dateString = "2018-08-29T11:00:00+02:00"
        val result = adapter.dateToJson(dateString)
        assertNotEquals(expected.time, result.time)
    }

    @Test
    fun test_shouldParseDateToString() {
        val expected = "2018-08-29T13:00:00+02:00"
        val date = getDate(2018, 7, 29, 11, 0, 0)
        val result = adapter.dateToJson(date)
        assertEquals(expected, result)
    }

    companion object {
        fun getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, seconds: Int): Date {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("CEST"))
            calendar.set(year, month, day, hour, minute, seconds)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.time
        }
    }
}