package com.yoletgr.gomaruart.feature.artgallery.presentation.screens

import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem

data class ArtGalleryUIState(
    val isLoading: Boolean = false,
    val artItems: List<ArtItem> = emptyList(),
    val errorMessage: String? = null
)