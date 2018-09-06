package com.kamilzagula.newsapp.data.model

import com.squareup.moshi.Json

data class NewsResponse(@Json(name = "totalResults")
                        val totalResults: Int = 0,
                        @Json(name = "articles")
                        val news: List<News>,
                        @Json(name = "status")
                        val status: String = "")