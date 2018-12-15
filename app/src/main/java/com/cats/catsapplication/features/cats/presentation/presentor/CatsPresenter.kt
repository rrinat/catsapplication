package com.cats.catsapplication.features.cats.presentation.presentor

import com.arellomobile.mvp.InjectViewState
import com.cats.catsapplication.R
import com.cats.catsapplication.core.mvp.Presenter
import com.cats.catsapplication.core.utils.ResourceProvider
import com.cats.catsapplication.core.utils.RxDecor
import com.cats.catsapplication.core.utils.async
import com.cats.catsapplication.core.utils.loadingView
import com.cats.catsapplication.features.cats.domain.interactor.CatsInteractor
import com.cats.catsapplication.features.cats.presentation.model.CatModel
import com.cats.catsapplication.features.cats.presentation.view.CatsView
import javax.inject.Inject

@InjectViewState
class CatsPresenter @Inject constructor(
    private val catsInteractor: CatsInteractor,
    private val resourceProvider: ResourceProvider) : Presenter<CatsView>() {

    private companion object {

        private const val CATS_COUNT = 10
    }

    private val catsLoading = loadingView({ }, { viewState.hideSwipeProgress() })

    override fun onFirstViewAttach() {
        loadCats()
    }

    fun onRefresh() {
        loadCats()
    }

    fun onFavoriteClick() {
        viewState.openFavorite()
    }

    private fun loadCats() {
        catsInteractor.getCats(CATS_COUNT)
            .map { cats -> cats.mapIndexed { index, cat ->  CatModel(cat.id, cat.url, index % 2 == 0) } }
            .async()
            .compose(RxDecor.loadingSingle(catsLoading))
            .subscribe(this::onCatsReceived, { viewState.showError(resourceProvider.getString(R.string.cats_message_error)) })
    }

    private fun onCatsReceived(cats: List<CatModel>) {
        viewState.showCats(cats)
    }
}