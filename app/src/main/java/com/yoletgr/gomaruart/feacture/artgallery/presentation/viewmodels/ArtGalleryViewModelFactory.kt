package com.yoletgr.gomaruart.feature.artgallery.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yoletgr.gomaruart.feature.artgallery.domain.usecase.*

class ArtGalleryViewModelFactory(
    private val getArtUseCase: GetArtUseCase,
    private val addArtUseCase: AddArtUseCase,
    private val deleteArtUseCase: DeleteArtUseCase,
    private val updateArtUseCase: UpdateArtUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArtGalleryViewModel(
            getArtUseCase = getArtUseCase,
            addArtUseCase = addArtUseCase,
            deleteArtUseCase = deleteArtUseCase,
            updateArtUseCase = updateArtUseCase
        ) as T
    }
}