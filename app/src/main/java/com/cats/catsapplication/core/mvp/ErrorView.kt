package com.cats.catsapplication.core.mvp

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface ErrorView {
    fun onReceiveError(message: String)
    fun hideError()
}