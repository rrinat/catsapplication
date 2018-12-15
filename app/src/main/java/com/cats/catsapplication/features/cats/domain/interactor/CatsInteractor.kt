package com.cats.catsapplication.features.cats.domain.interactor

import com.cats.catsapplication.core.domain.Cat
import io.reactivex.Single

interface CatsInteractor {

    fun getCats(limit: Int): Single<List<Cat>>
}