package com.yoletgr.gomaruart.feature.artgallery.data.repositories

import com.yoletgr.gomaruart.feacture.artgallery.data.datasources.remote.api.AuthApi
import com.yoletgr.gomaruart.feacture.artgallery.data.datasources.remote.models.LoginRequest
import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.api.ArtApi
import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.mapper.toDomain
import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.mapper.toDto
import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem
import com.yoletgr.gomaruart.feature.artgallery.domain.repositories.ArtRepository

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
    } catch (e: Exception) {
        emptyList()
    }

    override suspend fun addArtItem(item: ArtItem): Boolean = try {
        val response = artApi.create(item.toDto())
        response.isSuccessful
    } catch (e: Exception) {
        false
    }

    override suspend fun deleteArtItem(id: Int): Boolean = try {
        val response = artApi.delete(id)
        response.isSuccessful
    } catch (e: Exception) {
        false
    }

    // ACTUALIZADO: Ahora usa artApi.update para coincidir con tu interfaz
    override suspend fun updateArtItem(item: ArtItem): Boolean = try {
        val response = artApi.update(item.id, item.toDto())
        response.isSuccessful
    } catch (e: Exception) {
        false
    }

    override suspend fun login(username: String, password: String): Boolean = try {
        val response = authApi.login(LoginRequest(username, password))
        response.isSuccessful && response.body()?.success == true
    } catch (e: Exception) {
        false
    }
}