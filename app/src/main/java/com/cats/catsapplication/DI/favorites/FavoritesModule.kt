package com.cats.catsapplication.DI.favorites

import com.cats.catsapplication.api.ApiService
import com.cats.catsapplication.features.favorites.data.repository.FavoritesRepository
import com.cats.catsapplication.features.favorites.data.repository.FavoritesRepositoryIml
import com.cats.catsapplication.features.favorites.domain.interactor.FavoritesInteractor
import com.cats.catsapplication.features.favorites.domain.interactor.FavoritesInteractorIml
import dagger.Module
import dagger.Provides

@Module
class FavoritesModule {

    @Provides
    internal fun provideFavoritesRepository(apiService: ApiService): FavoritesRepository = FavoritesRepositoryIml(apiService)

    @Provides
    fun provideFavoritesInteractor(favoritesRepository: FavoritesRepository): FavoritesInteractor = FavoritesInteractorIml(favoritesRepository)
}