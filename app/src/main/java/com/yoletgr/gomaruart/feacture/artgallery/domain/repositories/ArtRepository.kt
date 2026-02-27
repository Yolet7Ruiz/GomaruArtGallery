package com.yoletgr.gomaruart.feature.artgallery.domain.repositories

import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem

interface ArtRepository {
    suspend fun getArtItems(): List<ArtItem>
    suspend fun addArtItem(item: ArtItem): Boolean
    suspend fun deleteArtItem(id: Int): Boolean
    suspend fun updateArtItem(item: ArtItem): Boolean
    suspend fun login(username: String, password: String): Boolean
}