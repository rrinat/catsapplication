package com.cats.catsapplication.features.favorites.data.repository

import com.cats.catsapplication.api.ApiService
import com.cats.catsapplication.core.domain.Cat
import io.reactivex.Completable
import io.reactivex.Single

class FavoritesRepositoryIml constructor(private val apiService: ApiService) : FavoritesRepository {

    override fun saveFavorite(cat: Cat): Single<String> {
        return Single.just("1")
    }

    override fun deleteFavorite(favoriteId: String): Completable {
        return Completable.complete()
    }
}