package com.cats.catsapplication.features.cats.presentation.view

import com.cats.catsapplication.core.mvp.Presentable
import com.cats.catsapplication.features.cats.presentation.model.CatModel

interface CatsView : Presentable {
    fun showCats(cats: List<CatModel>)
    fun hideSwipeProgress()
}