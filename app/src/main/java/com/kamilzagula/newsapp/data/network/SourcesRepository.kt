package com.kamilzagula.newsapp.data.network

import com.kamilzagula.newsapp.data.model.SourcesResponse
import io.reactivex.Single
import retrofit2.http.GET

interface SourcesRepository {
    @GET("sources")
    fun getSources(): Single<SourcesResponse>
}