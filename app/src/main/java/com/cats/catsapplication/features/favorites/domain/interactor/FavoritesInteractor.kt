package com.cats.catsapplication.features.favorites.domain.interactor

import com.cats.catsapplication.core.domain.Cat
import io.reactivex.Completable

interface FavoritesInteractor {

    fun saveFavorite(cat: Cat): Completable

    fun deleteFavorite(cat: Cat): Completable
}