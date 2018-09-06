package com.kamilzagula.newsapp.ui.favorites

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamilzagula.newsapp.R
import com.kamilzagula.newsapp.data.model.News
import com.kamilzagula.newsapp.databinding.FragmentFavoritesBinding
import com.kamilzagula.newsapp.ui.news.OnNewsClickedListener
import com.kamilzagula.newsapp.ui.news.details.NewsDetailsFragment
import org.koin.android.ext.android.inject

class FavoritesFragment : Fragment(), FavoritesContract.View, OnNewsClickedListener {

    private lateinit var binding: FragmentFavoritesBinding
    private val adapter: FavoritesAdapter by inject()
    private val presenter: FavoritesContract.Presenter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        binding.executePendingBindings()
        presenter.setupView(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    override fun showNews(news: List<News>) {
        adapter.items = news
    }

    override fun onNewsClicked(news: News) {
        fragmentManager?.beginTransaction()
                ?.replace(R.id.hostFragment, NewsDetailsFragment.newInstance(news))
                ?.addToBackStack(NewsDetailsFragment.TAG)
                ?.commit()
    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(context)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        adapter.onNewsClickedListener = this
        binding.recycler.adapter = adapter
    }
}