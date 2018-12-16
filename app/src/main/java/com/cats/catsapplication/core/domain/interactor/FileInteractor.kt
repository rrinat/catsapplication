package com.cats.catsapplication.core.domain.interactor

import io.reactivex.Single
import java.io.File

interface FileInteractor {

    fun getFile(url: String): Single<File>
}