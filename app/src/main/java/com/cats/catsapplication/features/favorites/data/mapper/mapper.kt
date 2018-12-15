package com.cats.catsapplication.features.favorites.data.mapper

import com.cats.catsapplication.core.data.enitity.CatEntity
import com.cats.catsapplication.core.domain.Cat

fun CatEntity.toDomain(): Cat {
    return Cat(id, url)
}

fun Cat.toEntity(): CatEntity {
    return CatEntity(id, url)
}