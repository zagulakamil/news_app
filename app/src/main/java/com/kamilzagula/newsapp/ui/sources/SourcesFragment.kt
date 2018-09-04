package com.kamilzagula.newsapp.ui.sources

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamilzagula.newsapp.R
import com.kamilzagula.newsapp.databinding.FragmentSourcesBinding

class SourcesFragment : Fragment() {
    private lateinit var binding: FragmentSourcesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sources, container, false)
        binding.executePendingBindings()
        return binding.root
    }
}