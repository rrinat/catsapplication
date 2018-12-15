package com.cats.catsapplication.api

import com.cats.catsapplication.api.response.CatImageResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/images/search")
    fun getImages(@Query("format") format: String,
                  @Query("limit") limit: Int,
                  @Query("size") size: String,
                  @Query("mime_types") mimeTypes: String): Single<List<CatImageResponse>>
}