package com.cats.catsapplication.features.cats.presentation.model

data class CatModel(val id: String, val url: String, val favoriteId: String = "") {

    fun isFavorite() = favoriteId.isNotEmpty()
}