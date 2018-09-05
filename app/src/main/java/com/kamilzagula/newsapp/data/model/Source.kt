package com.kamilzagula.newsapp.data.model

import com.squareup.moshi.Json

data class Source(@Json(name = "country")
                  val country: String = "",
                  @Json(name = "name")
                  val name: String = "",
                  @Json(name = "description")
                  val description: String = "",
                  @Json(name = "language")
                  val language: String = "",
                  @Json(name = "id")
                  val id: String = "",
                  @Json(name = "category")
                  val category: String = "",
                  @Json(name = "url")
                  val url: String = "")