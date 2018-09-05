package com.kamilzagula.newsapp.ui.sources

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamilzagula.newsapp.R
import com.kamilzagula.newsapp.data.model.Source
import com.kamilzagula.newsapp.databinding.FragmentSourcesBinding
import org.koin.android.ext.android.inject

class SourcesFragment : Fragment(), SourcesContract.View {

    private lateinit var binding: FragmentSourcesBinding
    private val presenter: SourcesContract.Presenter by inject()
    private val adapter = SourcesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sources, container, false)
        binding.executePendingBindings()
        presenter.setupView(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        binding.swipeRefresh.setOnRefreshListener { presenter.loadSources() }
    }

    override fun showSources(sources: List<Source>) {
        adapter.items = sources
    }

    override fun clearSources() {
        adapter.items = emptyList()
    }

    override fun showProgress() {
        binding.swipeRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        binding.swipeRefresh.isRefreshing = false
    }

    override fun showError() {
        binding.error.visibility = View.VISIBLE
    }

    override fun hideError() {
        binding.error.visibility = View.GONE
    }
}