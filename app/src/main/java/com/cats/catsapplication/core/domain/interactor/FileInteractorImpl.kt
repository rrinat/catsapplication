package com.cats.catsapplication.core.domain.interactor

import com.cats.catsapplication.core.data.repository.FileRepository

class FileInteractorImpl (private val fileRepository: FileRepository) : FileInteractor {

    override fun getFile(url: String) = fileRepository.getFile(url)
}