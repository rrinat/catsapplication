package com.cats.catsapplication.features.cats.DI

import com.cats.catsapplication.api.ApiService
import com.cats.catsapplication.features.cats.data.repository.CatsRepository
import com.cats.catsapplication.features.cats.data.repository.CatsRepositoryImpl
import com.cats.catsapplication.features.cats.domain.interactor.CatsInteractor
import com.cats.catsapplication.features.cats.domain.interactor.CatsInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class CatsModule {

    @Provides
    internal fun provideCatsRepository(apiService: ApiService): CatsRepository = CatsRepositoryImpl(apiService)

    @Provides
    fun provideCatsInteractor(catsRepository: CatsRepository): CatsInteractor = CatsInteractorImpl(catsRepository)
}