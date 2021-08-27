package com.masterwok.coinme.data.di

import android.content.Context
import com.masterwok.coinme.data.clients.moshi.adapters.UriAdapterFactory
import com.masterwok.coinme.data.clients.news.NewsApiClient
import com.masterwok.coinme.data.clients.okhttp.interceptors.InternetConnectionInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(UriAdapterFactory())
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(appContext: Context): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(InternetConnectionInterceptor(appContext))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        })
        .build()

    @Singleton
    @Provides
    fun provideNewsApiHttpRetrofitClient(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideNewsApiClient(retrofit: Retrofit): NewsApiClient = retrofit
        .create(NewsApiClient::class.java)

}
