package com.yoletgr.gomaruart.feature.artgallery.domain.usecase

import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem
import com.yoletgr.gomaruart.feature.artgallery.domain.repositories.ArtRepository

class AddArtUseCase(private val repository: ArtRepository) {
    suspend operator fun invoke(item: ArtItem): Boolean {
        return if (item.title.length >= 3 && item.price > 0) {
            repository.addArtItem(item)
        } else false
    }
}