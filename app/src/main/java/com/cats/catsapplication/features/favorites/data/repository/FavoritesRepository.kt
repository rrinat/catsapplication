package com.cats.catsapplication.features.favorites.data.repository

import com.cats.catsapplication.core.domain.Cat
import io.reactivex.Completable

interface FavoritesRepository {

    fun saveFavorite(cat: Cat): Completable

    fun deleteFavorite(cat: Cat): Completable
}