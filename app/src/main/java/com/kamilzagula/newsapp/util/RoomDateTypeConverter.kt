package com.kamilzagula.newsapp.util

import android.arch.persistence.room.TypeConverter
import java.util.*

class RoomDateTypeConverter {
    @TypeConverter
    fun toDate(value: Long?): Date? {
        if (value != null) {
            return Date(value)
        }
        return null
    }

    @TypeConverter
    fun toLong(value: Date?): Long? {
        if (value != null) {
            return value.time
        }
        return null
    }
}