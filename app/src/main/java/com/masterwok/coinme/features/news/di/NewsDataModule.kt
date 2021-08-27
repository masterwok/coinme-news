package com.masterwok.coinme.features.news.di

import com.masterwok.coinme.data.repositories.NewsRepositoryImpl
import com.masterwok.coinme.data.repositories.contracts.NewsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class NewsDataModule {

    @Binds
    internal abstract fun bind(viewModel: NewsRepositoryImpl): NewsRepository
}
