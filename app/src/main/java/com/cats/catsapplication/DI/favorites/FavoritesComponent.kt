package com.cats.catsapplication.DI.favorites

import dagger.Subcomponent

@FavoritesScope
@Subcomponent(modules = [FavoritesModule::class])
interface FavoritesComponent {
}