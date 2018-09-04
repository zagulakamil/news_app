package com.kamilzagula.newsapp.ui.news

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamilzagula.newsapp.R
import com.kamilzagula.newsapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        binding.executePendingBindings()
        return binding.root
    }
}