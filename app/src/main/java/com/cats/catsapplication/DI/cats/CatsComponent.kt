package com.cats.catsapplication.DI.cats

import com.cats.catsapplication.DI.AppComponentProvider
import com.cats.catsapplication.DI.favorites.FavoritesComponent
import com.cats.catsapplication.DI.favorites.FavoritesModule
import com.cats.catsapplication.features.cats.presentation.fragment.CatsFragment
import com.cats.catsapplication.features.cats.presentation.presentor.CatsPresenter
import dagger.Subcomponent

@CatsScope
@Subcomponent(modules = [CatsModule::class, FavoritesModule::class])
interface CatsComponent {

    fun inject(presenter: CatsPresenter)
    fun inject(fragment: CatsFragment)

    fun addFavoritesComponent(): FavoritesComponent

    companion object {
        fun build(): CatsComponent {
            return AppComponentProvider.get().addCatsComponent(CatsModule(), FavoritesModule())
        }
    }
}