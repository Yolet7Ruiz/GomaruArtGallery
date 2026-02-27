package com.yoletgr.gomaruart.feature.artgallery.domain.usecase

import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem
import com.yoletgr.gomaruart.feature.artgallery.domain.repositories.ArtRepository

class GetArtUseCase(private val repository: ArtRepository) {
    suspend operator fun invoke(): List<ArtItem> {
        return repository.getArtItems()
    }
}