package com.kamilzagula.newsapp.ui.news.details

import com.kamilzagula.newsapp.data.model.News
import com.kamilzagula.newsapp.ui.base.BasePresenter

interface NewsDetailsContract {
    interface View {
        fun getNews(): News
        fun showPhoto(urlToImage: String)
        fun setContent(title: String, description: String, url: String)
        fun itemExistsInFavorities()
        fun itemNotExistsInFavorities()
        fun itemAddedToFavorites()
        fun itemRemovedFromFavorites()
    }

    interface Presenter : BasePresenter<View> {
        fun changeFavoriteStatus()
    }
}
