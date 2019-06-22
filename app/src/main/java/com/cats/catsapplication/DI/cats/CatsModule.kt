package com.cats.catsapplication.DI.cats

import android.arch.lifecycle.ViewModelProvider
import com.cats.catsapplication.api.ApiService
import com.cats.catsapplication.core.domain.interactor.FileInteractor
import com.cats.catsapplication.core.utils.FileProvider
import com.cats.catsapplication.core.utils.ResourceProvider
import com.cats.catsapplication.features.cats.data.repository.CatsRepository
import com.cats.catsapplication.features.cats.data.repository.CatsRepositoryImpl
import com.cats.catsapplication.features.cats.domain.interactor.CatsInteractor
import com.cats.catsapplication.features.cats.domain.interactor.CatsInteractorImpl
import com.cats.catsapplication.features.cats.presentation.viewModelFactory.CatsViewModelFactory
import com.cats.catsapplication.features.favorites.domain.interactor.FavoritesInteractor
import dagger.Module
import dagger.Provides

@Module
class CatsModule {

    @Provides
    internal fun provideCatsRepository(apiService: ApiService): CatsRepository = CatsRepositoryImpl(apiService)

    @Provides
    fun provideCatsInteractor(catsRepository: CatsRepository): CatsInteractor = CatsInteractorImpl(catsRepository)

    @Provides
    fun provideViewModelFactory(
        catsInteractor: CatsInteractor,
        favoritesInteractor: FavoritesInteractor,
        fileInteractor: FileInteractor,
        fileProvider: FileProvider,
        resourceProvider: ResourceProvider
    ): ViewModelProvider.Factory = CatsViewModelFactory(catsInteractor, favoritesInteractor, fileInteractor, fileProvider, resourceProvider)
}