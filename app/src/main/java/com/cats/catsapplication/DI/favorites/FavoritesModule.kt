package com.cats.catsapplication.DI.favorites

import com.cats.catsapplication.core.data.AppDatabase
import com.cats.catsapplication.core.data.CatDao
import com.cats.catsapplication.features.favorites.data.repository.FavoritesRepository
import com.cats.catsapplication.features.favorites.data.repository.FavoritesRepositoryImpl
import com.cats.catsapplication.features.favorites.domain.interactor.FavoritesInteractor
import com.cats.catsapplication.features.favorites.domain.interactor.FavoritesInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class FavoritesModule {

    @Provides
    internal fun provideCatDao(appDatabase: AppDatabase): CatDao = appDatabase.catDao()

    @Provides
    internal fun provideFavoritesRepository(catDao: CatDao): FavoritesRepository = FavoritesRepositoryImpl(catDao)

    @Provides
    fun provideFavoritesInteractor(favoritesRepository: FavoritesRepository): FavoritesInteractor = FavoritesInteractorImpl(favoritesRepository)
}