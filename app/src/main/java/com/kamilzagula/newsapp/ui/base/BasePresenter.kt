package com.kamilzagula.newsapp.ui.base

interface BasePresenter<V> {
    fun onAttach()
    fun onDetach()
}