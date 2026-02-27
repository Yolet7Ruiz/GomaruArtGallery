package com.yoletgr.gomaruart.feature.artgallery.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoletgr.gomaruart.feature.artgallery.domain.entities.ArtItem
import com.yoletgr.gomaruart.feature.artgallery.domain.usecase.*
import com.yoletgr.gomaruart.feature.artgallery.presentation.screens.ArtGalleryUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtGalleryViewModel(
    private val getArtUseCase: GetArtUseCase,
    private val addArtUseCase: AddArtUseCase,
    private val deleteArtUseCase: DeleteArtUseCase,
    private val updateArtUseCase: UpdateArtUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtGalleryUIState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadArt()
    }

    fun loadArt() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val items = getArtUseCase()
                _uiState.update { it.copy(isLoading = false, artItems = items) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            }
        }
    }

    fun addArt(item: ArtItem) {
        viewModelScope.launch {
            val success = addArtUseCase(item)
            if (success) loadArt()
        }
    }

    fun deleteArt(id: Int) {
        viewModelScope.launch {
            val success = deleteArtUseCase(id)
            if (success) loadArt()
        }
    }

    fun updateArt(item: ArtItem) {
        viewModelScope.launch {
            val success = updateArtUseCase(item)
            if (success) loadArt()
        }
    }
}