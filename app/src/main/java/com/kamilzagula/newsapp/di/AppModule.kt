package com.kamilzagula.newsapp.di

import android.arch.persistence.room.Room
import com.kamilzagula.newsapp.App
import com.kamilzagula.newsapp.CommonSettings
import com.kamilzagula.newsapp.data.NewsDataSource
import com.kamilzagula.newsapp.data.local.AppDatabase
import com.kamilzagula.newsapp.data.local.NewsDao
import com.kamilzagula.newsapp.data.network.NewsRepository
import com.kamilzagula.newsapp.data.network.RetrofitFactory
import com.kamilzagula.newsapp.data.network.SourcesRepository
import com.kamilzagula.newsapp.ui.news.NewsAdapter
import com.kamilzagula.newsapp.ui.news.details.NewsDetailsContract
import com.kamilzagula.newsapp.ui.news.details.NewsDetailsPresenter
import com.kamilzagula.newsapp.ui.sources.SourcesContract
import com.kamilzagula.newsapp.ui.sources.SourcesPresenter
import com.kamilzagula.newsapp.util.JsonDateAdapter
import com.kamilzagula.newsapp.util.executors.ExecutorFactory
import com.kamilzagula.newsapp.util.rx.AppSchedulerProvider
import com.kamilzagula.newsapp.util.rx.SchedulerProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit

val rxModule = applicationContext {
    bean { AppSchedulerProvider() as SchedulerProvider }
}

fun appModule(app: App) = applicationContext {
    bean { app }
    bean { provideMoshi() }
    bean { RetrofitFactory(get(), get(), get()) }
    bean { provideRetrofit(get()) }
    bean { provideDatabase(get()) }
    bean { provideNewsDao(get()) }
    factory { ExecutorFactory() }
    factory { NewsDataSource(get(), CommonSettings.PAGE_SIZE, get()) }
    factory { NewsAdapter() }
}

val repositoriesModule = applicationContext {
    bean { provideSourceRepository(get()) }
    bean { provideNewsRepository(get()) }
}

val presentersModule = applicationContext {
    factory { SourcesPresenter(get(), get()) as SourcesContract.Presenter }
    factory { NewsDetailsPresenter(get(), get()) as NewsDetailsContract.Presenter }
}

internal fun provideNewsDao(appDatabase: AppDatabase): NewsDao {
    return appDatabase.newsDao()
}

internal fun provideDatabase(app: App): AppDatabase {
    return Room.databaseBuilder(
            app.applicationContext,
            AppDatabase::class.java,
            CommonSettings.DB_NAME
    ).build()
}

internal fun provideMoshi(): Moshi {
    return Moshi.Builder().add(JsonDateAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
}

internal fun provideSourceRepository(retrofit: Retrofit) = retrofit.create(SourcesRepository::class.java)

internal fun provideNewsRepository(retrofit: Retrofit) = retrofit.create(NewsRepository::class.java)

internal fun provideRetrofit(retrofitFactory: RetrofitFactory) = retrofitFactory.getRetrofitClient()

fun appModules(app: App) = listOf(rxModule, appModule(app), repositoriesModule, presentersModule)