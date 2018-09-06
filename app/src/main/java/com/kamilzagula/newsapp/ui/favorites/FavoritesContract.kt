package com.kamilzagula.newsapp.ui.favorites

import com.kamilzagula.newsapp.data.model.News
import com.kamilzagula.newsapp.ui.base.BasePresenter

interface FavoritesContract {
    interface View {
        fun showNews(news: List<News>)
    }

    interface Presenter : BasePresenter<View>
}

