package com.kamilzagula.newsapp.data.network

import com.kamilzagula.newsapp.data.model.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRepository {
    @GET("everything")
    fun getEverything(@Query("q") withQuery: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int): Single<NewsResponse>
}
