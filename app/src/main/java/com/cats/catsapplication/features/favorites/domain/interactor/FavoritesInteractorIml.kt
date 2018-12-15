package com.cats.catsapplication.features.favorites.domain.interactor

import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.features.favorites.data.repository.FavoritesRepository

class FavoritesInteractorIml (private val favoritesRepository: FavoritesRepository): FavoritesInteractor {

    override fun saveFavorite(cat: Cat) = favoritesRepository.saveFavorite(cat)

    override fun deleteFavorite(cat: Cat) = favoritesRepository.deleteFavorite(cat)

    override fun loadAllFavorites() = favoritesRepository.loadAllFavorites()
}