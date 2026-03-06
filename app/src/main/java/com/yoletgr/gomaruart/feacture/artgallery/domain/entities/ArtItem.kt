package com.yoletgr.gomaruart.feature.artgallery.domain.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ArtItem(
    val id: Int = 0,
    val title: String,
    val description: String,
    val price: Double,
    val filePath: String,

    @SerialName("categoryId")
    val categoryId: Int
)