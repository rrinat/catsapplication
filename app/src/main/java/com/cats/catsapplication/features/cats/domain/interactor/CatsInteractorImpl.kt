package com.cats.catsapplication.features.cats.domain.interactor

import com.cats.catsapplication.features.cats.data.repository.CatsRepository

class CatsInteractorImpl (private val catsRepository: CatsRepository): CatsInteractor {

    override fun getCats(limit: Int) = catsRepository.getCats(limit)
}