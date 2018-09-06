package com.kamilzagula.newsapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.github.bskierys.pine.Pine
import com.kamilzagula.newsapp.di.appModules
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        initializeKoin()

        if (BuildConfig.DEBUG) {
            initializeTimber()
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initializeKoin() {
        startKoin(this, appModules(this))
    }

    private fun initializeTimber() {
        Timber.plant(Pine.growDefault())
    }
}


