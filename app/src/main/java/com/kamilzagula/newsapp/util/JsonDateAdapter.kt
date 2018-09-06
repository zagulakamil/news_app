package com.kamilzagula.newsapp.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class JsonDateAdapter {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())

    @ToJson
    fun dateToJson(date: Date): String {
        return dateFormat.format(date)
    }

    @FromJson
    fun dateToJson(date: String): Date {
        return dateFormat.parse(date)
    }
}