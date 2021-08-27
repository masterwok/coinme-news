package com.masterwok.coinme.di

import android.content.Context
import com.masterwok.coinme.data.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class
//        RepositoryModule::class,
//        SubcomponentModules::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): ApplicationComponent
    }

//    fun portfolioComponent(): PortfolioComponent.Factory

}