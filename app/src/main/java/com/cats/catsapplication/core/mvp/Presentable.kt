package com.cats.catsapplication.core.mvp

import android.net.Uri
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface Presentable : LoadingView, ErrorView, MvpView {

    fun showToast(message: String) // TODO нужно перенести в роутер

    fun openShareFile(uri: Uri, mimeType: String) // TODO нужно перенести в роутер
}