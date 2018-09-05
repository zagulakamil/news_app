package com.kamilzagula.newsapp.data.network

import com.kamilzagula.newsapp.data.model.NewsResponse
import com.kamilzagula.newsapp.data.model.SourcesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    fun getEverything(/*@Query("q") withQuery: String,*/ @Query("page") page: Int, @Query("pageSize") pageSize: Int): Single<NewsResponse>

    @GET("sources")
    fun getSources(): Single<SourcesResponse>
}
