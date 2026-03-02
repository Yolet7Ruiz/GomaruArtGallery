package com.yoletgr.gomaruart.feature.artgallery.data.repositories

import com.yoletgr.gomaruart.feacture.artgallery.data.datasources.remote.api.AuthApi
import com.yoletgr.gomaruart.feacture.artgallery.data.datasources.remote.models.LoginRequest
import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.api.ArtApi
import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.mapper.toDomain
import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.mapper.toDto
import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem
import com.yoletgr.gomaruart.feature.artgallery.domain.repositories.ArtRepository
import java.io.IOException

class ArtGalleryRepositoryImpl(
    private val artApi: ArtApi,
    private val authApi: AuthApi
) : ArtRepository {

    override suspend fun getArtItems(): List<ArtItem> = try {
        val response = artApi.fetchAll()
        if (response.isSuccessful) {
            response.body()?.map { it.toDomain() } ?: emptyList()
        } else {
            emptyList()
        }
    } catch (e: IOException) {
        emptyList()
    }

    override suspend fun addArtItem(item: ArtItem): Boolean = try {
        artApi.create(item.toDto()).isSuccessful
    } catch (e: IOException) {
        false
    }

    override suspend fun deleteArtItem(id: Int): Boolean = try {
        artApi.delete(id).isSuccessful
    } catch (e: IOException) {
        false
    }

    override suspend fun updateArtItem(item: ArtItem): Boolean = try {
        artApi.update(item.id, item.toDto()).isSuccessful
    } catch (e: IOException) {
        false
    }

    override suspend fun login(username: String, password: String): Boolean = try {
        val response = authApi.login(LoginRequest(username, password))
        response.isSuccessful && response.body()?.success == true
    } catch (e: IOException) {
        false
    }
}