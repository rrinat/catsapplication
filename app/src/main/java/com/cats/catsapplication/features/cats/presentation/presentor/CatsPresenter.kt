package com.cats.catsapplication.features.cats.presentation.presentor

import com.arellomobile.mvp.InjectViewState
import com.cats.catsapplication.core.mvp.Presenter
import com.cats.catsapplication.core.utils.async
import com.cats.catsapplication.features.cats.domain.interactor.CatsInteractor
import com.cats.catsapplication.features.cats.presentation.model.CatModel
import com.cats.catsapplication.features.cats.presentation.view.CatsView
import javax.inject.Inject

@InjectViewState
class CatsPresenter @Inject constructor(private val catsInteractor: CatsInteractor) : Presenter<CatsView>() {

    private companion object {

        private const val CATS_COUNT = 10
    }

    override fun onFirstViewAttach() {
        loadCats()
    }

    private fun loadCats() {
        catsInteractor.getCats(CATS_COUNT)
            .map { cats -> cats.mapIndexed { index, cat ->  CatModel(cat.id, cat.url, index % 2 == 0) } }
            .async()
            .subscribe(this::onCatsReceived, {
                // TODO
            })
    }

    private fun onCatsReceived(cats: List<CatModel>) {
        viewState.showCats(cats)
    }
}