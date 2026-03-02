package com.yoletgr.gomaruart.feacture.artgallery.data.datasources.remote.api

import com.yoletgr.gomaruart.feacture.artgallery.data.datasources.remote.models.LoginRequest
import com.yoletgr.gomaruart.feacture.artgallery.data.datasources.remote.models.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}