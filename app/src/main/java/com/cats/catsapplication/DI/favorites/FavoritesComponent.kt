package com.cats.catsapplication.DI.favorites

import com.cats.catsapplication.features.favorites.presentation.fragment.FavoritesFragment
import com.cats.catsapplication.features.favorites.presentation.presenter.FavoritesPresenter
import dagger.Subcomponent

@FavoritesScope
@Subcomponent()
interface FavoritesComponent {

    fun inject(fragment: FavoritesFragment)
    fun inject(presenter: FavoritesPresenter)
}