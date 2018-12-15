package com.cats.catsapplication.features.favorites.presentation.mapper

import com.cats.catsapplication.core.data.enitity.CatEntity
import com.cats.catsapplication.core.domain.Cat

fun Cat.toEntity(): CatEntity {
    return CatEntity(id, url)
}