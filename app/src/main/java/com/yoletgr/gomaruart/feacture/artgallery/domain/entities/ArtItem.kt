package com.yoletgr.gomaruart.feature.artgallery.domain.entities

data class ArtItem(
    val id: Int = 0,
    val title: String,
    val description: String,
    val price: Double,
    val filePath: String,
    val categoryId: Int
)