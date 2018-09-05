package com.kamilzagula.newsapp.ui.sources

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.kamilzagula.newsapp.data.model.SourcesResponse
import com.kamilzagula.newsapp.data.repositories.SourcesRepository
import com.kamilzagula.newsapp.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class SourcesPresenter(
    private val sourcesRepository: SourcesRepository,
    private val schedulerProvider: SchedulerProvider
) : SourcesContract.Presenter, LifecycleObserver {

    lateinit var view : SourcesContract.View
    private val compositeDisposable = CompositeDisposable()

    override fun loadSources() {
        loadSources(showProgress = false)
    }

    override fun setupView(view: SourcesContract.View) {
        this.view = view
        if (view is LifecycleOwner) {
            view.lifecycle.addObserver(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME) override fun onAttach() {
        loadSources(showProgress = true)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE) override fun onDetach() {
        compositeDisposable.clear()
    }

    private fun loadSources(showProgress: Boolean) {
        compositeDisposable.add(sourcesRepository.getSources()
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe {
                    view.hideError()
                    view.clearSources()
                    if (showProgress) {
                        view.showProgress()
                    }
                }
                .subscribeBy(onSuccess = this::sourcesLoaded, onError = this::sourcesLoadFailed))
    }

    private fun sourcesLoaded(response: SourcesResponse) {
        Timber.d("Sources loaded in size of ${response.sources.size}")
        view.hideProgress()
        view.showSources(response.sources)
    }

    private fun sourcesLoadFailed(throwable: Throwable) {
        Timber.w("Sources load failed $throwable")
        view.showError()
    }
}