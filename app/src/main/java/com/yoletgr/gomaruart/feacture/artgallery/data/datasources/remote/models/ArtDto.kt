package com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ArtDto(
    @SerialName("id_obra") val id_obra: Int? = null,
    @SerialName("titulo") val titulo: String,
    @SerialName("descripcion") val descripcion: String,
    @SerialName("precio") val precio: Double,
    @SerialName("imagen_url") val imagen_url: String,

    // ⬇️ ESTO ES LO QUE EL SERVER BUSCA
    @SerialName("categoryId")
    val id_categoria: Int
)