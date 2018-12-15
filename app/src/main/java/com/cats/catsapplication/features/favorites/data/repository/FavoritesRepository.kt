package com.cats.catsapplication.features.favorites.data.repository

import com.cats.catsapplication.core.domain.Cat
import io.reactivex.Completable
import io.reactivex.Single

interface FavoritesRepository {

    fun saveFavorite(cat: Cat): Single<String>

    fun deleteFavorite(favoriteId: String): Completable
}