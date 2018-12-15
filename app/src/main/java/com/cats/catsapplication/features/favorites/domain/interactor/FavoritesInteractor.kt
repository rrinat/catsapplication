package com.cats.catsapplication.features.favorites.domain.interactor

import com.cats.catsapplication.core.domain.Cat
import io.reactivex.Completable
import io.reactivex.Single

interface FavoritesInteractor {

    fun saveFavorite(cat: Cat): Single<String>

    fun deleteFavorite(favoriteId: String): Completable
}