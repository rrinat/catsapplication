package com.cats.catsapplication.features.cats.presentation.viewModelFactory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.cats.catsapplication.core.domain.interactor.FileInteractor
import com.cats.catsapplication.core.utils.FileProvider
import com.cats.catsapplication.core.utils.ResourceProvider
import com.cats.catsapplication.features.cats.domain.interactor.CatsInteractor
import com.cats.catsapplication.features.cats.presentation.viewModel.CatsViewModel
import com.cats.catsapplication.features.favorites.domain.interactor.FavoritesInteractor
import javax.inject.Inject



class CatsViewModelFactory @Inject constructor(
    private val catsInteractor: CatsInteractor,
    private val favoritesInteractor: FavoritesInteractor,
    private val fileInteractor: FileInteractor,
    private val fileProvider: FileProvider,
    private val resourceProvider: ResourceProvider) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatsViewModel(catsInteractor, favoritesInteractor, fileInteractor, fileProvider, resourceProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}