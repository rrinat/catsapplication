package com.cats.catsapplication.DI

import com.cats.catsapplication.DI.cats.CatsComponent
import com.cats.catsapplication.DI.cats.CatsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    fun addCatsComponent(catsModule: CatsModule): CatsComponent
}