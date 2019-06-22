package com.cats.catsapplication.DI.favorites

import com.cats.catsapplication.DI.cats.CatsComponentProvider
import com.cats.catsapplication.features.favorites.presentation.fragment.FavoritesFragment
import dagger.Subcomponent

@FavoritesScope
@Subcomponent()
interface FavoritesComponent {

    fun inject(fragment: FavoritesFragment)

    companion object {
        fun build(): FavoritesComponent {
            return CatsComponentProvider.get().addFavoritesComponent()
        }
    }
}