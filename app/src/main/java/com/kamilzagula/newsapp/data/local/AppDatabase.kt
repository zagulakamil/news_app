package com.kamilzagula.newsapp.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.kamilzagula.newsapp.util.RoomDateTypeConverter

@Database(entities = arrayOf(NewsEntity::class), version = 1)
@TypeConverters(RoomDateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}