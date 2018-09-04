package com.kamilzagula.newsapp.di

import android.content.Context
import com.kamilzagula.newsapp.App
import com.kamilzagula.newsapp.R
import com.kamilzagula.newsapp.util.rx.AppSchedulerProvider
import com.kamilzagula.newsapp.util.rx.SchedulerProvider
import okhttp3.OkHttpClient
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val rxModule = applicationContext {
    bean { AppSchedulerProvider() as SchedulerProvider }
}

fun appModule(app: App) = applicationContext {
    bean { app }
    bean { retrofit(get(), get()) }
}

fun retrofit(context: Context, schedulerProvider: SchedulerProvider): Retrofit {
    val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", context.getString(R.string.api_key))
                        .build()
                return@addInterceptor chain.proceed(request)
            }
            .build()

    return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(schedulerProvider.io()))
            .client(okHttpClient)
            .build()
}

fun appModules(app: App) = listOf(rxModule, appModule(app))