package com.yoletgr.gomaruart.feacture.artgallery.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("token") val token: String?,
    @SerializedName("username") val username: String?
)