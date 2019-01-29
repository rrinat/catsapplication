package com.cats.catsapplication.core.data.repository

import com.cats.catsapplication.R
import com.cats.catsapplication.api.ApiService
import com.cats.catsapplication.core.utils.FileProvider
import com.cats.catsapplication.core.utils.ResourceProvider
import io.reactivex.Single
import java.io.File
import java.io.IOException

class FileRepositoryImpl (private val apiService: ApiService,
                          private val fileProvider: FileProvider,
                          private val resourceProvider: ResourceProvider) : FileRepository {


    override fun getFile(url: String): Single<File> {
        return Single.defer {
            val directory = fileProvider.getDownloadsFolder()
            return@defer directory?.let {
                apiService.getFile(url)
                    .map {
                        val file = File(directory, getFileNameFromUrl(url))
                        fileProvider.writeResponseBodyToFile(it, file)
                        return@map file
                    }
            } ?: Single.error(IOException(resourceProvider.getString(R.string.file_storage_error)))
        }
    }

    private fun getFileNameFromUrl(url: String): String {
        return url.substring(url.lastIndexOf(File.separator))
    }

}