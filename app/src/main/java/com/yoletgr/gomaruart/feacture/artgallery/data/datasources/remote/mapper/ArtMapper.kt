package com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.mapper

import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.models.ArtDto
import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem

fun ArtDto.toDomain() = ArtItem(
    id = id,
    title = title,
    description = body,
    price = price,
    filePath = filePath,
    categoryId = categoryId
)

fun ArtItem.toDto() = ArtDto(
    id = id,
    title = title,
    body = description,
    price = price,
    filePath = filePath,
    categoryId = categoryId
)