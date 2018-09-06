package com.kamilzagula.newsapp.ui.news

import com.kamilzagula.newsapp.data.model.News

interface OnNewsClickedListener {
    fun onNewsClicked(news: News)
}