package com.kamilzagula.newsapp.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormattedString(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    return dateFormat.format(this)
}