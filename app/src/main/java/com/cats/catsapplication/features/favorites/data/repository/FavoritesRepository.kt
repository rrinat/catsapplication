package com.cats.catsapplication.features.favorites.data.repository

import com.cats.catsapplication.core.domain.Cat
import io.reactivex.Completable
import io.reactivex.Single

interface FavoritesRepository {

    fun saveFavorite(cat: Cat): Completable

    fun deleteFavorite(cat: Cat): Completable

    fun loadAllFavorites(): Single<List<Cat>>
}