package com.cats.catsapplication.features.favorites.presentation.viewModelFactory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.cats.catsapplication.core.domain.interactor.FileInteractor
import com.cats.catsapplication.core.utils.FileProvider
import com.cats.catsapplication.core.utils.ResourceProvider
import com.cats.catsapplication.features.favorites.domain.interactor.FavoritesInteractor
import com.cats.catsapplication.features.favorites.presentation.viewModel.FavoritesViewModel
import javax.inject.Inject

class FavoritesViewModelFactory @Inject constructor(
    private val favoritesInteractor: FavoritesInteractor,
    private val fileInteractor: FileInteractor,
    private val fileProvider: FileProvider,
    private val resourceProvider: ResourceProvider) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(favoritesInteractor, fileInteractor, fileProvider, resourceProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}