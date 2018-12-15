package com.cats.catsapplication.core.mvp

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface LoadingView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgress()
    fun hideProgress()
}