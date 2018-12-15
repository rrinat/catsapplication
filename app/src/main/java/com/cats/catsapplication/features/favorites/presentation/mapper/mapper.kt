package com.cats.catsapplication.features.favorites.presentation.mapper

import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.features.cats.presentation.model.CatModel

fun CatModel.toDomain(): Cat {
    return Cat(id, url)
}