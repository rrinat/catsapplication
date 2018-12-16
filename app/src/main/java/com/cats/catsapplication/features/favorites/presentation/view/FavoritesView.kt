package com.cats.catsapplication.features.favorites.presentation.view

import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.core.mvp.Presentable

interface FavoritesView : Presentable {
    fun showFavorites(cats: List<Cat>)
    fun showEmptyView()
    fun hideEmptyView()
}