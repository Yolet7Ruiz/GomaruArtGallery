package com.yoletgr.gomaruart.feature.artgallery.domain.usecase

import com.yoletgr.gomaruart.feature.artgallery.domain.repositories.ArtRepository

class DeleteArtUseCase(private val repository: ArtRepository) {
    suspend operator fun invoke(id: Int): Boolean {
        return if (id > 0) repository.deleteArtItem(id) else false
    }
}