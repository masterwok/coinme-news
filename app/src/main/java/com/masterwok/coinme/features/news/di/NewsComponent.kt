package com.masterwok.coinme.features.news.di

import com.masterwok.coinme.di.modules.ViewModelFactoryModule
import com.masterwok.coinme.features.news.NewsFragment
import dagger.Subcomponent

@NewsScope
@Subcomponent(
    modules = [
        ViewModelFactoryModule::class,
        NewsViewModelModule::class,
        NewsDataModule::class
    ]
)
interface NewsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): NewsComponent
    }

    fun inject(fragment: NewsFragment)

}