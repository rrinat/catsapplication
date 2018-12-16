package com.cats.catsapplication.features.favorites.presentation.presenter

import android.Manifest
import com.arellomobile.mvp.InjectViewState
import com.cats.catsapplication.R
import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.core.domain.interactor.FileInteractor
import com.cats.catsapplication.core.mvp.Presenter
import com.cats.catsapplication.core.utils.FileProvider
import com.cats.catsapplication.core.utils.ResourceProvider
import com.cats.catsapplication.core.utils.RxDecor
import com.cats.catsapplication.core.utils.async
import com.cats.catsapplication.features.favorites.domain.interactor.FavoritesInteractor
import com.cats.catsapplication.features.favorites.presentation.view.FavoritesView
import com.tbruyelle.rxpermissions2.RxPermissions
import javax.inject.Inject

@InjectViewState
class FavoritesPresenter @Inject constructor(
    private val favoritesInteractor: FavoritesInteractor,
    private val fileInteractor: FileInteractor,
    private val fileProvider: FileProvider,
    private val resourceProvider: ResourceProvider) : Presenter<FavoritesView>() {

    private companion object {
        private const val MIME_TYPE_IMAGE = "image/*"
    }

    private var cats: List<Cat> = emptyList()

    override fun onFirstViewAttach() {
        loadFavorites()
    }

    private fun onFavoritesReceived(cats: List<Cat>) {
        this.cats = cats
        viewState.showFavorites(cats)

        if (cats.isEmpty()) {
            viewState.showEmptyView()
        } else {
            viewState.hideEmptyView()
        }
    }

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

    private fun updateFavorites(cat: Cat) {
        onFavoritesReceived(cats.filter { it.id != cat.id })
    }

    private fun dispatchStoragePermissionResult(isGranted: Boolean, url: String) {
        if (isGranted.not()) {
            viewState.showError(resourceProvider.getString(R.string.permissions_message_not_granted))
        } else {
            loadFile(url)
        }
    }

    private fun loadFile(url: String) {
        fileInteractor.getFile(url)
            .map { fileProvider.getUriForFile(it) }
            .async()
            .compose(lifecycle())
            .compose(RxDecor.loadingSingle(viewState))
            .subscribe({ viewState.openShareFile(it, MIME_TYPE_IMAGE) }, { viewState.showError(resourceProvider.getString(R.string.cats_error_download_file)) })
    }
}