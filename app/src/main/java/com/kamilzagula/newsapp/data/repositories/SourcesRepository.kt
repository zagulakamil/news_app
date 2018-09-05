package com.kamilzagula.newsapp.data.repositories

import com.kamilzagula.newsapp.data.network.NewsApi

class SourcesRepository(private val newsApi: NewsApi) {

    fun getSources() = newsApi.getSources()
}

