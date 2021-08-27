package com.masterwok.coinme.features.news.di

import androidx.lifecycle.ViewModel
import com.masterwok.coinme.di.annotations.ViewModelKey
import com.masterwok.coinme.features.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NewsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    internal abstract fun bindViewModel(viewModel: NewsViewModel): ViewModel
}