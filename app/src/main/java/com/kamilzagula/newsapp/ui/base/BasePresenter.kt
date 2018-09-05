package com.kamilzagula.newsapp.ui.base

interface BasePresenter<V> {
    fun onDetach()
    fun onAttach()
    fun setupView(view: V)
}