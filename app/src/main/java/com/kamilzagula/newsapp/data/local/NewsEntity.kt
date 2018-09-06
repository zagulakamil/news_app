package com.kamilzagula.newsapp.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class NewsEntity(
        @PrimaryKey var title: String,
        var description: String,
        var url: String,
        var urlToImage: String?
        )