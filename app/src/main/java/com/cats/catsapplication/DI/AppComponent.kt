package com.cats.catsapplication.DI

import com.cats.catsapplication.DI.cats.CatsComponent
import com.cats.catsapplication.DI.cats.CatsModule
import com.cats.catsapplication.DI.favorites.FavoritesComponent
import com.cats.catsapplication.DI.favorites.FavoritesModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DBModule::class])
interface AppComponent {

    fun addCatsComponent(catsModule: CatsModule, favoritesModule: FavoritesModule): CatsComponent
    fun addFavoritesComponent(favoritesModule: FavoritesModule): FavoritesComponent
}