package com.cats.catsapplication.DI

import com.cats.catsapplication.features.cats.DI.CatsComponent
import com.cats.catsapplication.features.cats.DI.CatsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    fun addCatsComponent(catsModule: CatsModule): CatsComponent
}