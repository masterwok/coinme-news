package com.masterwok.coinme

import android.app.Application
import com.masterwok.coinme.di.ApplicationComponent
import com.masterwok.coinme.di.DaggerApplicationComponent

class App : Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent
            .factory()
            .create(this.applicationContext)
    }

}