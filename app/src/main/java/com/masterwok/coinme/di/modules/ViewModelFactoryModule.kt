package com.masterwok.coinme.di.modules

import androidx.lifecycle.ViewModelProvider
import com.masterwok.coinme.di.factories.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: DaggerViewModelFactory
    ): ViewModelProvider.Factory

}