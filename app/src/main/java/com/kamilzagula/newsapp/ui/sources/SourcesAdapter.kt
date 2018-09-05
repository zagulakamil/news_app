package com.kamilzagula.newsapp.ui.sources

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kamilzagula.newsapp.data.model.Source
import com.kamilzagula.newsapp.databinding.ItemSourceBinding

class SourcesAdapter : RecyclerView.Adapter<SourcesAdapter.ViewHolder>() {

    var items = emptyList<Source>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSourceBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    class ViewHolder(private val binding: ItemSourceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Source) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}