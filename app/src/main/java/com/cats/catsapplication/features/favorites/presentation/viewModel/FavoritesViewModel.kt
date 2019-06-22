package com.cats.catsapplication.features.favorites.presentation.viewModel

import android.Manifest
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.cats.catsapplication.R
import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.core.domain.interactor.FileInteractor
import com.cats.catsapplication.core.mvvm.BaseViewModel
import com.cats.catsapplication.core.utils.FileProvider
import com.cats.catsapplication.core.utils.ResourceProvider
import com.cats.catsapplication.core.utils.RxDecor
import com.cats.catsapplication.core.utils.async
import com.cats.catsapplication.features.favorites.domain.interactor.FavoritesInteractor
import com.tbruyelle.rxpermissions2.RxPermissions

class FavoritesViewModel constructor(
    private val favoritesInteractor: FavoritesInteractor,
    private val fileInteractor: FileInteractor,
    private val fileProvider: FileProvider,
    private val resourceProvider: ResourceProvider) : BaseViewModel() {

    private companion object {
        private const val MIME_TYPE_IMAGE = "image/*"
    }

    private var cats = MutableLiveData<List<Cat>>()

    init {
        loadFavorites()
    }

    fun getCats(): LiveData<List<Cat>> = cats

    fun onDeleteClick(cat: Cat) {
        favoritesInteractor.deleteFavorite(cat)
            .async()
            .compose(lifecycle<Any>())
            .subscribe({ updateFavorites(cat) }, {})
    }

    fun onDownloadClick(cat: Cat, rxPermissions: RxPermissions) {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe({ this.dispatchStoragePermissionResult(it, cat.url) }, {})
    }

    private fun loadFavorites() {
        favoritesInteractor.loadAllFavorites()
            .async()
            .subscribe(this::onFavoritesReceived, {})
    }

    private fun onFavoritesReceived(cats: List<Cat>) {
        this.cats.value = cats
    }

    private fun updateFavorites(cat: Cat) {
        cats.value?.let {cats -> onFavoritesReceived(cats.filter { it.id != cat.id }) }
    }

    private fun dispatchStoragePermissionResult(isGranted: Boolean, url: String) {
        if (isGranted.not()) {
             error.value = resourceProvider.getString(R.string.permissions_message_not_granted)
        } else {
            loadFile(url)
        }
    }

    private fun loadFile(url: String) {
        fileInteractor.getFile(url)
            .map { fileProvider.getUriForFile(it) }
            .async()
            .compose(lifecycle())
            .compose(RxDecor.loadingSingle(loading))
            .subscribe({
               // viewState.openShareFile(it, MIME_TYPE_IMAGE)
            }, { error.value = resourceProvider.getString(R.string.cats_error_download_file) })
    }
}