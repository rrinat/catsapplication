package com.cats.catsapplication.features.cats.data.repository

import com.cats.catsapplication.api.ApiService
import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.features.cats.data.mapper.toEntity
import io.reactivex.Single

class CatsRepositoryImpl constructor(private val apiService: ApiService) : CatsRepository {

    companion object {
        private const val FORMAT = "json"
        private const val SIZE = "small"
        private const val TYPE_IMAGE = "png"
    }

    override fun getCats(limit: Int): Single<List<Cat>> {
        return apiService.getImages(FORMAT, limit, SIZE, TYPE_IMAGE)
            .map { response -> response.map { it.toEntity() } }
            .map { cats -> cats.filter { it.id.isNotBlank() && it.url.isNotBlank() } }
    }
}