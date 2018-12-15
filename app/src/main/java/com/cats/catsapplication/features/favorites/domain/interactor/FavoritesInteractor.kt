package com.cats.catsapplication.features.favorites.domain.interactor

import com.cats.catsapplication.core.domain.Cat
import io.reactivex.Completable
import io.reactivex.Single

interface FavoritesInteractor {

    fun saveFavorite(cat: Cat): Completable

    fun deleteFavorite(cat: Cat): Completable

    fun loadAllFavorites(): Single<List<Cat>>
}