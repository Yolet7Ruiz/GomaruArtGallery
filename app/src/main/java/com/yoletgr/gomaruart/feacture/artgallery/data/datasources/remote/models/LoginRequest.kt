package com.yoletgr.gomaruart.feacture.artgallery.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)