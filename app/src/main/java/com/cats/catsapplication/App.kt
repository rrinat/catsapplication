package com.cats.catsapplication

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.cats.catsapplication.DI.DependencyProviders


class App : Application() {

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onCreate() {
        super.onCreate()
        DependencyProviders.init(this)
    }
}