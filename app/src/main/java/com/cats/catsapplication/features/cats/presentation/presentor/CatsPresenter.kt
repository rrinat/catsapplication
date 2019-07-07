package com.cats.catsapplication.features.cats.presentation.presentor

import android.Manifest
import com.arellomobile.mvp.InjectViewState
import com.cats.catsapplication.R
import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.core.domain.interactor.FileInteractor
import com.cats.catsapplication.core.mvp.Presenter
import com.cats.catsapplication.core.utils.*
import com.cats.catsapplication.features.cats.domain.interactor.CatsInteractor
import com.cats.catsapplication.features.cats.presentation.model.CatModel
import com.cats.catsapplication.features.cats.presentation.view.CatsView
import com.cats.catsapplication.features.favorites.domain.interactor.FavoritesInteractor
import com.cats.catsapplication.features.favorites.presentation.mapper.toDomain
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

typealias Cats = List<Cat>

@InjectViewState
class CatsPresenter @Inject constructor(
    private val catsInteractor: CatsInteractor,
    private val favoritesInteractor: FavoritesInteractor,
    private val fileInteractor: FileInteractor,
    private val fileProvider: FileProvider,
    private val resourceProvider: ResourceProvider) : Presenter<CatsView>() {

    private companion object {

        private const val CATS_COUNT = 10
        private const val MIME_TYPE_IMAGE = "image/*"
    }

    private val catsLoading = loadingView({ viewState.showSwipeProgress() }, { viewState.hideSwipeProgress() })
    private var cats: List<CatModel> = emptyList()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadCats()
    }

    fun onRefresh() {
        loadCats()
    }

    fun onFavoriteMenuClick() {
        viewState.openFavorite()
    }

    fun onFavoriteClick(catModel: CatModel) {
        if (catModel.isFavorite) {
            deleteFavorite(catModel)
        } else {
            addFavorite(catModel)
        }
    }

    fun onDownloadClick(catModel: CatModel, rxPermissions: RxPermissions) {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe({ this.dispatchStoragePermissionResult(it, catModel.url) }, {})
    }

    private fun loadCats() {
        Single.zip<Cats, Cats, List<CatModel>>(catsInteractor.getCats(CATS_COUNT), favoritesInteractor.loadAllFavorites(), BiFunction { t1, t2 -> combineCats(t1, t2) })
            .async()
            .compose(lifecycle())
            .compose(RxDecor.loadingSingle(catsLoading))
            .subscribe(this::onCatsReceived, { viewState.showError(resourceProvider.getString(R.string.cats_error_download_images)) })
    }

    private fun onCatsReceived(cats: List<CatModel>) {
        this.cats = cats
        viewState.showCats(cats)
    }

    private fun addFavorite(catModel: CatModel) {
        favoritesInteractor.saveFavorite(catModel.toDomain())
            .async()
            .compose(lifecycle<Any>())
            .subscribe({ updateCats(catModel, true) }, {})
    }

    private fun deleteFavorite(catModel: CatModel) {
        favoritesInteractor.deleteFavorite(catModel.toDomain())
            .async()
            .compose(lifecycle<Any>())
            .subscribe({ updateCats(catModel, false) }, {})
    }

    private fun updateCats(catModel: CatModel, isFavorite: Boolean) {
        cats = cats.map { cat -> if (cat.id != catModel.id) cat else cat.copy(isFavorite = isFavorite) }
        viewState.showCats(cats)
    }

    private fun combineCats(remoteCats: List<Cat>, favoriteCats: List<Cat>): List<CatModel> {
        return remoteCats.map { remoteCat -> CatModel(remoteCat.id, remoteCat.url, favoriteCats.any { it.id == remoteCat.id }) }
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