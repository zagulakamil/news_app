package com.kamilzagula.newsapp.di

import com.kamilzagula.newsapp.App
import com.kamilzagula.newsapp.data.network.NewsApi
import com.kamilzagula.newsapp.data.network.RetrofitFactory
import com.kamilzagula.newsapp.data.repositories.SourcesRepository
import com.kamilzagula.newsapp.ui.sources.SourcesContract
import com.kamilzagula.newsapp.ui.sources.SourcesPresenter
import com.kamilzagula.newsapp.util.rx.AppSchedulerProvider
import com.kamilzagula.newsapp.util.rx.SchedulerProvider
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit

val rxModule = applicationContext {
    bean { AppSchedulerProvider() as SchedulerProvider }
}

fun appModule(app: App) = applicationContext {
    bean { app }
    bean { RetrofitFactory(get(), get()) }
    bean { provideRetrofit(get()) }
    bean { provideNewsApi(get()) }
}

val repositoriesModule = applicationContext {
    factory { SourcesRepository(get()) }
}

val presentersModule = applicationContext {
    factory { SourcesPresenter(get(), get()) as SourcesContract.Presenter }
}

internal fun provideNewsApi(retrofit: Retrofit) = retrofit.create(NewsApi::class.java)

internal fun provideRetrofit(retrofitFactory: RetrofitFactory) = retrofitFactory.getRetrofitClient()

fun appModules(app: App) = listOf(rxModule, appModule(app), repositoriesModule, presentersModule)