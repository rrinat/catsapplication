package com.cats.catsapplication.features.cats.data.mapper

import com.cats.catsapplication.api.response.CatImageResponse
import com.cats.catsapplication.core.domain.Cat

fun CatImageResponse.toDomain(): Cat {
    return Cat(id.orEmpty(), url.orEmpty())
}