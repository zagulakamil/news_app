package com.kamilzagula.newsapp.data

import android.arch.paging.PageKeyedDataSource
import com.kamilzagula.newsapp.CommonSettings
import com.kamilzagula.newsapp.data.model.News
import com.kamilzagula.newsapp.data.network.NewsRepository
import com.kamilzagula.newsapp.ui.news.NewsLoadingStatusChangedListener
import com.kamilzagula.newsapp.util.rx.SchedulerProvider
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class NewsDataSource(
        private val newsRepository: NewsRepository,
        private val pageSize: Int,
        private val schedulerProvider: SchedulerProvider
) : PageKeyedDataSource<Int, News>() {

    var loadingStatusChangedListener: NewsLoadingStatusChangedListener? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>) {
        newsRepository.getEverything(CommonSettings.DEFAULT_QUERY, 1, pageSize)
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { loadingStatusChangedListener?.onLoadingStarts() }
                .doFinally { loadingStatusChangedListener?.onLoadingStops() }
                .subscribeBy(onSuccess = {
                    callback.onResult(it.news, 0, it.news.size, null, 2)
                }, onError = {
                    Timber.w(it, "Error while getting news")
                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        newsRepository.getEverything(CommonSettings.DEFAULT_QUERY, params.key, pageSize)
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { loadingStatusChangedListener?.onLoadingStarts() }
                .doFinally { loadingStatusChangedListener?.onLoadingStops() }
                .subscribeBy(onSuccess = {
                    callback.onResult(it.news, params.key + 1)
                }, onError = {
                    Timber.w(it, "Error while getting news")
                })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        newsRepository.getEverything(CommonSettings.DEFAULT_QUERY, params.key, pageSize)
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { loadingStatusChangedListener?.onLoadingStarts() }
                .doFinally { loadingStatusChangedListener?.onLoadingStops() }
                .subscribeBy(onSuccess = {
                    callback.onResult(it.news, params.key - 1)
                }, onError = {
                    Timber.w(it, "Error while getting news")
                })
    }

}