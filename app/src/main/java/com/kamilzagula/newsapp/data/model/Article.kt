package com.kamilzagula.newsapp.data.model

import com.squareup.moshi.Json

data class Article(@Json(name = "publishedAt")
                   val publishedAt: String = "",
                   @Json(name = "author")
                   val author: String = "",
                   @Json(name = "urlToImage")
                   val urlToImage: String = "",
                   @Json(name = "description")
                   val description: String = "",
                   @Json(name = "source")
                   val source: Source,
                   @Json(name = "title")
                   val title: String = "",
                   @Json(name = "url")
                   val url: String = "")