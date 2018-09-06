package com.kamilzagula.newsapp.ui.news.details

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kamilzagula.newsapp.R
import com.kamilzagula.newsapp.data.model.News
import com.kamilzagula.newsapp.databinding.FragmentNewsDetailsBinding
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject

class NewsDetailsFragment : Fragment(), NewsDetailsContract.View {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val presenter: NewsDetailsContract.Presenter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)
        binding.executePendingBindings()
        presenter.setupView(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addToFavorite.setOnClickListener {
            presenter.changeFavoriteStatus()
        }
    }

    override fun getNews(): News {
        return arguments?.getParcelable(NEWS_OBJECT) as News
    }

    override fun showPhoto(urlToImage: String) {
        Picasso.get()
                .load(urlToImage)
                .fit()
                .centerCrop()
                .into(binding.img)
    }

    override fun setContent(title: String, description: String, url: String) {
        binding.title.text = title
        binding.description.text = description
        binding.url.text = url
    }

    override fun itemExistsInFavorities() {
        context?.let {
            binding.addToFavorite.setImageDrawable(it.getDrawable(R.drawable.ic_baseline_favorite_24px))
        }
    }

    override fun itemNotExistsInFavorities() {
        context?.let {
            binding.addToFavorite.setImageDrawable(it.getDrawable(R.drawable.ic_heart_outline))
        }
    }

    override fun itemAddedToFavorites() {
        context?.let {
            binding.addToFavorite.setImageDrawable(it.getDrawable(R.drawable.ic_baseline_favorite_24px))
            Toast.makeText(it, R.string.news_added_to_favorites, Toast.LENGTH_LONG).show()
        }
    }

    override fun itemRemovedFromFavorites() {
        context?.let {
            binding.addToFavorite.setImageDrawable(it.getDrawable(R.drawable.ic_heart_outline))
            Toast.makeText(it, R.string.news_removed_from_favorites, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        val TAG = this::class.java.canonicalName!!
        private const val NEWS_OBJECT = "NEWS_OBJECT"

        fun newInstance(news: News): NewsDetailsFragment =
                NewsDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(NEWS_OBJECT, news)
                    }
                }
    }
}