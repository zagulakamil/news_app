package com.kamilzagula.newsapp.util

import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class RoomDateTypeConverterTest {

    private lateinit var converter: RoomDateTypeConverter

    @Before
    fun setUp() {
        converter = RoomDateTypeConverter()
    }

    @Test
    fun test_ifParseNullLong() {
        assertNull(converter.toDate(null))
    }

    @Test
    fun test_ifParseNullDate() {
        assertNull(converter.toLong(null))
    }

    @Test
    fun test_parseDateToLong() {
        val result = converter.toLong(JsonDateAdapterTest.getDate(2018, 7, 29, 11, 0, 0))
        assertEquals(result, 1535540400000)
    }

    @Test
    fun test_parseLongToDate() {
        val result = converter.toDate(1535540400000)
        assertEquals(JsonDateAdapterTest.getDate(2018, 7, 29, 11, 0, 0), result)
    }
}