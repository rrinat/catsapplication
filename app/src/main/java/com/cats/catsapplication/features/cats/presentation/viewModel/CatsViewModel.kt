package com.cats.catsapplication.features.cats.presentation.viewModel

import android.Manifest
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.cats.catsapplication.R
import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.core.domain.interactor.FileInteractor
import com.cats.catsapplication.core.mvvm.BaseViewModel
import com.cats.catsapplication.core.utils.*
import com.cats.catsapplication.features.cats.domain.interactor.CatsInteractor
import com.cats.catsapplication.features.cats.presentation.model.CatModel
import com.cats.catsapplication.features.favorites.domain.interactor.FavoritesInteractor
import com.cats.catsapplication.features.favorites.presentation.mapper.toDomain
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Single
import io.reactivex.functions.BiFunction

typealias Cats = List<Cat>

class CatsViewModel constructor(private val catsInteractor: CatsInteractor,
                                private val favoritesInteractor: FavoritesInteractor,
                                private val fileInteractor: FileInteractor,
                                private val fileProvider: FileProvider,
                                private val resourceProvider: ResourceProvider) : BaseViewModel() {

    private companion object {

        private const val CATS_COUNT = 10
        private const val MIME_TYPE_IMAGE = "image/*"
    }


    private var cats = MutableLiveData<List<CatModel>>()
    private var swipeProgress = MutableLiveData<Boolean>().apply { value = false }


    init {
        loadCats()
    }

    fun getCats(): LiveData<List<CatModel>> = cats

    fun getSwipeProgress(): LiveData<Boolean> = swipeProgress

    fun onRefresh() {
        loadCats()
    }

    fun onFavoriteMenuClick() {
       //viewState.openFavorite()
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
            .compose(RxDecor.loadingSingle(swipeProgress))
            .subscribe(cats::setValue, {
                error.value = resourceProvider.getString(R.string.cats_error_download_images)
            })
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
        cats.value?.let {value ->
            cats.value = value.map { cat -> if (cat.id != catModel.id) cat else cat.copy(isFavorite = isFavorite) }
        }
    }

    private fun combineCats(remoteCats: List<Cat>, favoriteCats: List<Cat>): List<CatModel> {
        return remoteCats.map { remoteCat -> CatModel(remoteCat.id, remoteCat.url, favoriteCats.any { it.id == remoteCat.id }) }
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
               //  viewState.openShareFile(it, MIME_TYPE_IMAGE)
            }, { error.value = resourceProvider.getString(R.string.cats_error_download_file) })
    }
}