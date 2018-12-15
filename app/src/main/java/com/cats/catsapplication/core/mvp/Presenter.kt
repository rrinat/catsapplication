package com.cats.catsapplication.core.mvp

import com.arellomobile.mvp.MvpPresenter
import com.cats.catsapplication.core.utils.LifecycleProvider
import com.cats.catsapplication.core.utils.LifecycleTransformer

abstract class Presenter<T : Presentable> : MvpPresenter<T>() {

    private val provider = LifecycleProvider()

    override fun destroyView(view: T) {
        super.destroyView(view)
        provider.unsubscribe()
    }

    protected fun <T> lifecycle(): LifecycleTransformer<T, T> = provider.lifecycle()

    protected fun showProgress() {
        viewState.showProgress()
    }

    protected fun hideProgress() {
        viewState.hideProgress()
    }
}