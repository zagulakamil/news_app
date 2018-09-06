package com.kamilzagula.newsapp.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class NewsEntity(
        @PrimaryKey var title: String,
        var description: String,
        var url: String,
        var urlToImage: String?,
        var publishedAt: Date?
        )