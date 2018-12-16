package com.cats.catsapplication.DI

import android.content.Context
import com.cats.catsapplication.BuildConfig
import com.cats.catsapplication.DI.cats.CatsComponent
import com.cats.catsapplication.DI.cats.CatsModule
import com.cats.catsapplication.DI.favorites.FavoritesModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DBModule::class])
interface AppComponent {

    companion object {
        fun build(context: Context): AppComponent {
            return DaggerAppComponent
                .builder()
                .appModule(AppModule(context))
                .networkModule(NetworkModule(BuildConfig.ENDPOINT, BuildConfig.API_KEY))
                .dBModule(DBModule(context))
                .build()
        }
    }

    fun addCatsComponent(catsModule: CatsModule, favoritesModule: FavoritesModule): CatsComponent
}