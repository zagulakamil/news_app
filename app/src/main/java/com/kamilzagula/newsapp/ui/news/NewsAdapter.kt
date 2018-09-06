package com.kamilzagula.newsapp.ui.news

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kamilzagula.newsapp.data.model.News
import com.kamilzagula.newsapp.databinding.ItemNewsBinding
import com.squareup.picasso.Picasso

class NewsAdapter : PagedListAdapter<News, NewsAdapter.ViewHolder>(News.DIFF_CALLBACK) {

    var onNewsClickedListener: OnNewsClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onNewsClickedListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    class ViewHolder(
            private val binding: ItemNewsBinding,
            private val onNewsClickedListener: OnNewsClickedListener?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News?) {
            news?.let { n ->
                binding.root.setOnClickListener { onNewsClickedListener?.onNewsClicked(n) }
                binding.item = n
                if (n.urlToImage.orEmpty().isNotBlank()) {
                    Picasso.get()
                            .load(n.urlToImage)
                            .fit()
                            .centerCrop()
                            .into(binding.img)
                }
                binding.executePendingBindings()
            }
        }
    }
}