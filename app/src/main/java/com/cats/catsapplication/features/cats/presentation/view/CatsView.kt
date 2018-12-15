package com.cats.catsapplication.features.cats.presentation.view

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.cats.catsapplication.core.mvp.Presentable
import com.cats.catsapplication.features.cats.presentation.model.CatModel

interface CatsView : Presentable {
    fun showCats(cats: List<CatModel>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideSwipeProgress()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openFavorite()
}