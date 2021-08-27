package com.masterwok.coinme.di

import android.content.Context
import com.masterwok.coinme.data.di.NetworkModule
import com.masterwok.coinme.di.modules.SubcomponentModules
import com.masterwok.coinme.features.news.di.NewsComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        SubcomponentModules::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }

    fun newsComponent(): NewsComponent.Factory

}