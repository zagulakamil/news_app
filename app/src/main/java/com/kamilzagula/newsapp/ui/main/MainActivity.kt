package com.kamilzagula.newsapp.ui.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.kamilzagula.newsapp.R
import com.kamilzagula.newsapp.databinding.ActivityMainBinding
import com.kamilzagula.newsapp.ui.base.BaseActivity
import com.kamilzagula.newsapp.ui.favorites.FavoritesFragment
import com.kamilzagula.newsapp.ui.news.NewsFragment
import com.kamilzagula.newsapp.ui.sources.SourcesFragment

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.navigationBar.inflateMenu(R.menu.bottom_navigation)
        binding.navigationBar.setOnNavigationItemSelectedListener(this)
        supportActionBar?.title = "${getString(R.string.news)} ${getString(R.string.powered_by)}"
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                supportActionBar?.title = "${getString(R.string.favorite)} ${getString(R.string.powered_by)}"
                loadFragment(FavoritesFragment())
            }
            R.id.sources -> {
                supportActionBar?.title = "${getString(R.string.sources)} ${getString(R.string.powered_by)}"
                loadFragment(SourcesFragment())
            }
            R.id.news -> {
                supportActionBar?.title = "${getString(R.string.news)} ${getString(R.string.powered_by)}"
                loadFragment(NewsFragment())
            }
        }
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.hostFragment, fragment)
                .commit()
    }
}