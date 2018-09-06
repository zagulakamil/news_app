package com.kamilzagula.newsapp.ui.favorites

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kamilzagula.newsapp.data.model.News
import com.kamilzagula.newsapp.databinding.ItemNewsBinding
import com.kamilzagula.newsapp.ui.news.OnNewsClickedListener
import com.squareup.picasso.Picasso

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    var onNewsClickedListener: OnNewsClickedListener? = null
    var items = emptyList<News>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onNewsClickedListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    class ViewHolder(
            private val binding: ItemNewsBinding,
            private val onNewsClickedListener: OnNewsClickedListener?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.item = news
            binding.root.setOnClickListener { onNewsClickedListener?.onNewsClicked(news) }
            if (news.urlToImage.orEmpty().isNotBlank()) {
                Picasso.get()
                        .load(news.urlToImage)
                        .fit()
                        .centerCrop()
                        .into(binding.img)
            }
            binding.executePendingBindings()
        }
    }
}