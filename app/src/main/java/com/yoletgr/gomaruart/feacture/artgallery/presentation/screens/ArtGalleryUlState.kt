package com.yoletgr.gomaruart.feature.artgallery.presentation.screens

import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem

data class ArtGalleryUIState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val artItems: List<ArtItem> = emptyList(),
    val errorMessage: String? = null,


    // Para saber si estamos editando
    val selectedArtForEdit: ArtItem? = null,

    // Estado de la autenticación
    val authError: String? = null
)