package com.yoletgr.gomaruart.feature.artgallery.data.repositories

import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.api.ArtApi
import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.mapper.toDomain
import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.mapper.toDto
import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem
import com.yoletgr.gomaruart.feature.artgallery.domain.repositories.ArtRepository
import java.io.IOException

class ArtGalleryRepositoryImpl(
    private val api: ArtApi
) : ArtRepository {

    override suspend fun getArtItems(): List<ArtItem> = try {
        api.fetchAll().map { it.toDomain() }
    } catch (e: IOException) {
        emptyList()
    }

    override suspend fun addArtItem(item: ArtItem): Boolean = try {
        api.create(item.toDto()).isSuccessful
    } catch (e: IOException) {
        false
    }

    override suspend fun deleteArtItem(id: Int): Boolean = try {
        api.delete(id).isSuccessful
    } catch (e: IOException) {
        false
    }

    override suspend fun updateArtItem(item: ArtItem): Boolean = try {
        api.update(item.id, item.toDto()).isSuccessful
    } catch (e: IOException) {
        false
    }

    override suspend fun login(username: String, password: String): Boolean {
        return username == "admin@art.com" && password == "admin123"
    }
}