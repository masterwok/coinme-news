package com.masterwok.coinme.di

import android.app.Application

class AppInjector {

    companion object {

        private lateinit var application: Application

        private val appComponent: AppComponent by lazy {
            DaggerAppComponent
                .factory()
                .create(application.applicationContext)
        }

        val newsComponent by lazy {
            appComponent.newsComponent().create()
        }

        fun init(application: Application) {
            this.application = application
        }

    }
}

