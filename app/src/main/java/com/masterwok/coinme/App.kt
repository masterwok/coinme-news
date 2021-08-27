package com.masterwok.coinme

import android.app.Application
import com.masterwok.coinme.di.AppInjector

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }

}