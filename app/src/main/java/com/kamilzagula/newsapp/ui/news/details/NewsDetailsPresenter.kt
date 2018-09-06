package com.kamilzagula.newsapp.ui.news.details

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.kamilzagula.newsapp.data.local.NewsDao
import com.kamilzagula.newsapp.data.local.NewsEntity
import com.kamilzagula.newsapp.data.model.News
import com.kamilzagula.newsapp.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class NewsDetailsPresenter(
    private val newsDao: NewsDao,
    private val schedulerProvider: SchedulerProvider
): NewsDetailsContract.Presenter, LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: NewsDetailsContract.View
    private lateinit var news: News
    private var isInFavorites: Boolean = false

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE) override fun onDetach() {
        compositeDisposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME) override fun onAttach() {
        compositeDisposable.add(newsDao.getNewsByTitle(news.title)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeBy(onSuccess = {
                    view.itemExistsInFavorities()
                    isInFavorites = true
                }, onError = {
                    view.itemNotExistsInFavorities()
                    isInFavorites = false
                }))

        news.urlToImage?.let {
            if (it.isNotBlank()) {
                view.showPhoto(it)
            }
        }
        view.setContent(news.title, news.description, news.url)
    }

    override fun setupView(view: NewsDetailsContract.View) {
        this.view = view
        this.news = view.getNews()
        if (view is LifecycleOwner) {
            view.lifecycle.addObserver(this)
        }
    }

    override fun changeFavoriteStatus() {
        if (isInFavorites) {
            removeFromFavorites()
        } else {
            addToFavorities()
        }
    }

    private fun removeFromFavorites() {
        Completable.fromSingle(
                Single.just(news)
                        .map {
                            NewsEntity(title = it.title,
                                    description = it.description,
                                    url = it.url,
                                    urlToImage = it.urlToImage,
                                    publishedAt = it.publishedAt)
                        }.doOnSuccess {
                            newsDao.remove(it)
                        })
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeBy(onComplete = {
                    isInFavorites = false
                    view.itemRemovedFromFavorites()
                })
    }

    private fun addToFavorities() {
        Completable.fromSingle(
                Single.just(news)
                        .map {
                            NewsEntity(title = it.title,
                                    description = it.description,
                                    url = it.url,
                                    urlToImage = it.urlToImage,
                                    publishedAt = it.publishedAt)
                        }.doOnSuccess {
                            newsDao.insert(it)
                        })
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeBy(onComplete = {
                    isInFavorites = true
                    view.itemAddedToFavorites()
                })
    }
}