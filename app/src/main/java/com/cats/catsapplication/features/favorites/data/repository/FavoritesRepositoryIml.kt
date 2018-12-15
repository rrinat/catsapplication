package com.cats.catsapplication.features.favorites.data.repository

import com.cats.catsapplication.core.data.CatDao
import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.features.favorites.presentation.mapper.toEntity
import io.reactivex.Completable

class FavoritesRepositoryIml constructor(private val catDao: CatDao) : FavoritesRepository {

    override fun saveFavorite(cat: Cat): Completable {
        return Completable.fromAction {
            catDao.insertCats(cat.toEntity())
        }
    }

    override fun deleteFavorite(cat: Cat): Completable {
        return Completable.fromAction { catDao.deleteCats(cat.toEntity()) }
    }
}