package com.kamilzagula.newsapp.data.network

import android.content.Context
import com.kamilzagula.newsapp.R
import com.kamilzagula.newsapp.util.rx.SchedulerProvider
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


class RetrofitFactory(
        private val context: Context,
        private val schedulerProvider: SchedulerProvider,
        private val moshi: Moshi
) {

    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(schedulerProvider.io()))
                .client(getHttpClient())
                .baseUrl(context.getString(R.string.api_base_url))
                .build()
    }

    private fun getHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor { chain ->
                    val request = chain.request()
                            .newBuilder()
                            .addHeader("Authorization", context.getString(R.string.api_key))
                            .build()
                    return@addInterceptor chain.proceed(request)
                }
                .build()
    }
}