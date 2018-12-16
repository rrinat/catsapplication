package com.cats.catsapplication.DI

import android.app.Application
import com.cats.catsapplication.DI.cats.CatsComponent
import com.cats.catsapplication.DI.cats.CatsComponentProvider
import com.cats.catsapplication.DI.favorites.FavoritesComponent
import com.cats.catsapplication.DI.favorites.FavoritesComponentProvider

object DependencyProviders {

    fun init(app: Application) {
        AppComponentProvider.init { AppComponent.build(app) }
        CatsComponentProvider.init { CatsComponent.build() }
        FavoritesComponentProvider.init { FavoritesComponent.build() }
    }
}