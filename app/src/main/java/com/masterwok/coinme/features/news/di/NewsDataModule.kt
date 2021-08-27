package com.masterwok.coinme.features.news.di

import com.masterwok.coinme.data.clients.news.NewsApiClient
import com.masterwok.coinme.data.repositories.NewsRepositoryImpl
import com.masterwok.coinme.data.repositories.contracts.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class NewsDataModule {

    @NewsScope
    @Provides
    fun provideNewsRepository(
        newsApiClient: NewsApiClient
    ): NewsRepository = NewsRepositoryImpl(
        newsApiClient,
//        "828a6ecc708a4f969a8f60460c4a6e76"
        "b2348b23497a4c0f9c59693d5d8d2cef"
    )

}