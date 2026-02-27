package com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.models

data class ArtDto(
    val id: Int,
    val title: String,
    val body: String,
    val price: Double,
    val filePath: String,
    val categoryId: Int
)