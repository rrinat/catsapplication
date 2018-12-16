package com.cats.catsapplication.core.data.repository

import io.reactivex.Single
import java.io.File

interface FileRepository {

    fun getFile(url: String): Single<File>
}