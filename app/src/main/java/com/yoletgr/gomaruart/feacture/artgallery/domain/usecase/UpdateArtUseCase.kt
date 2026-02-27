package com.yoletgr.gomaruart.feature.artgallery.domain.usecase

import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem
import com.yoletgr.gomaruart.feature.artgallery.domain.repositories.ArtRepository

class UpdateArtUseCase(private val repository: ArtRepository) {
    suspend operator fun invoke(item: ArtItem): Boolean {
        return if (item.id > 0) repository.updateArtItem(item) else false
    }
}