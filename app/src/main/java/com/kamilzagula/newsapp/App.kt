package com.kamilzagula.newsapp

import android.app.Application
import com.github.bskierys.pine.Pine
import com.kamilzagula.newsapp.di.appModules
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()

        if (BuildConfig.DEBUG) {
            initializeTimber()
        }
    }

    private fun initializeKoin() {
        startKoin(this, appModules(this))
    }

    private fun initializeTimber() {
        Timber.plant(Pine.growDefault())
    }
}

