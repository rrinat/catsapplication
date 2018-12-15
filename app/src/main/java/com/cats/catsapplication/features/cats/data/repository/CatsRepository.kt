package com.cats.catsapplication.features.cats.data.repository

import com.cats.catsapplication.core.domain.Cat
import io.reactivex.Single

interface CatsRepository {

    fun getCats(limit: Int): Single<List<Cat>>
}