package com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.api

import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.models.ArtDto
import retrofit2.Response
import retrofit2.http.*

interface ArtApi {
    @GET("art")
    suspend fun fetchAll(): List<ArtDto>

    @POST("art")
    suspend fun create(@Body dto: ArtDto): Response<Unit>

    @DELETE("art/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Unit>

    @PUT("art/{id}")
    suspend fun update(@Path("id") id: Int, @Body dto: ArtDto): Response<Unit>
}