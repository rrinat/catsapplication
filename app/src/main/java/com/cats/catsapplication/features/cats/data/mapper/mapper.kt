package com.cats.catsapplication.features.cats.data.mapper

import com.cats.catsapplication.api.response.CatImageResponse
import com.cats.catsapplication.features.cats.domain.Cat

fun CatImageResponse.toEntity(): Cat {
    return Cat(id.orEmpty(), url.orEmpty())
}