package com.kamilzagula.newsapp.ui.news

import android.arch.paging.PagedList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamilzagula.newsapp.CommonSettings
import com.kamilzagula.newsapp.R
import com.kamilzagula.newsapp.data.NewsDataSource
import com.kamilzagula.newsapp.data.model.News
import com.kamilzagula.newsapp.databinding.FragmentNewsBinding
import com.kamilzagula.newsapp.ui.news.details.NewsDetailsFragment
import com.kamilzagula.newsapp.util.executors.BackgroundThreadExecutor
import com.kamilzagula.newsapp.util.executors.ExecutorFactory
import com.kamilzagula.newsapp.util.executors.UiThreadExecutor
import org.koin.android.ext.android.inject

class NewsFragment : Fragment(), NewsLoadingStatusChangedListener, OnNewsClickedListener {

    private lateinit var binding: FragmentNewsBinding
    private var isFragmentAlreadyLoaded: Boolean = false
    private val adapter: NewsAdapter by inject()
    private val newsDataSource: NewsDataSource by inject()
    private val executorFactory: ExecutorFactory by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        binding.executePendingBindings()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isFragmentAlreadyLoaded = true
        setupRecycler()
        binding.swipeRefresh.setOnRefreshListener {
            adapter.submitList(getPagedList())
        }
    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(context)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))

        newsDataSource.loadingStatusChangedListener = this
        adapter.onNewsClickedListener = this
        binding.recycler.adapter = adapter
        adapter.submitList(getPagedList())
    }

    private fun getPagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(0)
            .setPageSize(CommonSettings.PAGE_SIZE)
            .build()

    private fun getPagedList(): PagedList<News> = PagedList.Builder<Int, News>(newsDataSource, getPagedListConfig())
            .setNotifyExecutor(executorFactory.createExecutor<UiThreadExecutor>())
            .setFetchExecutor(executorFactory.createExecutor<BackgroundThreadExecutor>())
            .build()

    override fun onNewsClicked(news: News) {
        fragmentManager?.beginTransaction()
                ?.replace(R.id.hostFragment, NewsDetailsFragment.newInstance(news))
                ?.addToBackStack(NewsDetailsFragment.TAG)
                ?.commit()
    }

    override fun onLoadingStarts() {
        binding.progress.show()
    }

    override fun onLoadingStops() {
        binding.progress.hide()
        binding.swipeRefresh.isRefreshing = false
    }
}