package com.cats.catsapplication

import android.annotation.SuppressLint
import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.cats.catsapplication.DI.*


class App : Application() {

    companion object {

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }

        @SuppressLint("StaticFieldLeak")
        private lateinit var instance: App

        fun getComponents() = Components

        fun getAppComponent() = instance.appComponent
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule(BuildConfig.ENDPOINT, BuildConfig.API_KEY))
            .build()
    }
}