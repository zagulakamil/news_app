package com.kamilzagula.newsapp.ui.sources

import com.kamilzagula.newsapp.data.model.Source
import com.kamilzagula.newsapp.ui.base.BasePresenter

interface SourcesContract {

    interface View {
        fun showSources(sources: List<Source>)
        fun clearSources()
        fun showProgress()
        fun hideProgress()
        fun showError()
        fun hideError()
    }

    interface Presenter : BasePresenter<View> {
        fun loadSources()
    }

}

