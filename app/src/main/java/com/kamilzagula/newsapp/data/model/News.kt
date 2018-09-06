package com.kamilzagula.newsapp.data.model

import android.os.Parcelable
import android.support.v7.util.DiffUtil
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class News(@Json(name = "publishedAt")
                   val publishedAt: Date? = null,
                @Json(name = "author")
                   val author: String = "",
                @Json(name = "urlToImage")
                   val urlToImage: String? = null,
                @Json(name = "description")
                   val description: String = "",
                @Json(name = "source")
                   val source: Source?,
                @Json(name = "title")
                   val title: String = "",
                @Json(name = "url")
                   val url: String = ""
) : Parcelable {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem === newItem

            override fun areContentsTheSame(oldItem: News?, newItem: News?): Boolean = oldItem == newItem
        }
    }
}