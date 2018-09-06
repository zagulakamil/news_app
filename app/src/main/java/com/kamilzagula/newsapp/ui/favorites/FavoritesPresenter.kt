package com.kamilzagula.newsapp.ui.favorites

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.kamilzagula.newsapp.data.local.NewsDao
import com.kamilzagula.newsapp.data.local.NewsEntity
import com.kamilzagula.newsapp.data.model.News
import com.kamilzagula.newsapp.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class FavoritesPresenter(
        private val newsDao: NewsDao,
        private val schedulerProvider: SchedulerProvider
) : FavoritesContract.Presenter, LifecycleObserver {

    private lateinit var view: FavoritesContract.View
    private val compositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE) override fun onDetach() {
        compositeDisposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME) override fun onAttach() {
        compositeDisposable.add(
                newsDao.getAll()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .flatMapObservable { Observable.fromIterable(it) }
                        .map {
                            News(
                                    title = it.title,
                                    description = it.description,
                                    url = it.url,
                                    urlToImage = it.urlToImage,
                                    publishedAt = it.publishedAt,
                                    author = "",
                                    source = null
                            )
                        }
                        .toList()
                        .subscribeBy(onSuccess = {
                            view.showNews(it)
                        })
        )
    }

    override fun setupView(view: FavoritesContract.View) {
        this.view = view
        if (view is LifecycleOwner) {
            view.lifecycle.addObserver(this)
        }
    }

}