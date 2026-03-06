package com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.mapper

import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.models.ArtDto
import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem

fun ArtDto.toDomain() = ArtItem(
    id = id_obra ?: 0,
    title = titulo,
    description = descripcion,
    price = precio,
    filePath = imagen_url,
    categoryId = id_categoria
)

fun ArtItem.toDto() = ArtDto(
    id_obra = if (id == 0) null else id, // null para nuevos, ID para editar
    titulo = title,
    descripcion = description,
    precio = price,
    imagen_url = filePath,
    id_categoria = categoryId
)