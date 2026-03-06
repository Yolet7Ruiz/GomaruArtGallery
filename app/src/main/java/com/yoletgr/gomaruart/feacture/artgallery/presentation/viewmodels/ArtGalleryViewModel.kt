package com.yoletgr.gomaruart.feature.artgallery.presentation.viewmodels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    private val updateArtUseCase: UpdateArtUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtGalleryUIState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    // ESTADO DE FORMULARIOS
    var loginEmail by mutableStateOf("") ; private set
    var loginPassword by mutableStateOf("") ; private set
    var loginError by mutableStateOf(false) ; private set

    var titleInput by mutableStateOf("") ; private set
    var descriptionInput by mutableStateOf("") ; private set
    var priceInput by mutableStateOf("") ; private set
    var categoryIdInput by mutableStateOf(1) ; private set
    var selectedImageUri by mutableStateOf<Uri?>(null) ; private set

    init {
        loadArt()
    }

    //  FUNCIONES DE EVENTOS
    fun onLoginEmailChange(newValue: String) { loginEmail = newValue; loginError = false }
    fun onLoginPasswordChange(newValue: String) { loginPassword = newValue; loginError = false }

    fun onTitleChange(newValue: String) { titleInput = newValue }
    fun onDescriptionChange(newValue: String) { descriptionInput = newValue }
    fun onPriceChange(newValue: String) { priceInput = newValue }
    fun onCategoryChange(newValue: Int) { categoryIdInput = newValue }
    fun onImageChange(uri: Uri) { selectedImageUri = uri }

    //  LÓGICA DE NAVEGACIÓN Y LIMPIEZA
    fun onEditClick(obra: ArtItem) {
        _uiState.update { it.copy(selectedArtForEdit = obra) }
    }

    fun onCancelEdit() {
        _uiState.update { it.copy(selectedArtForEdit = null) }
        clearForm()
    }

    fun clearForm() {
        titleInput = ""
        descriptionInput = ""
        priceInput = ""
        categoryIdInput = 1
        selectedImageUri = null
    }

    fun loadArtToEdit(obra: ArtItem) {
        titleInput = obra.title
        descriptionInput = obra.description
        priceInput = obra.price.toString()
        categoryIdInput = obra.categoryId
        selectedImageUri = if (obra.filePath.isNotEmpty()) Uri.parse(obra.filePath) else null
    }

    // LLAMADAS A USE CASES
    fun performLogin() {
        if (loginEmail.isBlank() || loginPassword.isBlank()) {
            loginError = true
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val success = loginUseCase(loginEmail, loginPassword)
                _uiState.update { it.copy(isLoading = false, isLoggedIn = success) }
                if (!success) loginError = true
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun loadArt() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val items = getArtUseCase()
                _uiState.update { it.copy(isLoading = false, artItems = items) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun addArt(item: ArtItem) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val success = addArtUseCase(item)
                if (success) {
                    loadArt()
                    clearForm()
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun deleteArt(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                if (deleteArtUseCase(id)) loadArt()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun updateArt(item: ArtItem) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                if (updateArtUseCase(item)) {
                    loadArt()
                    onCancelEdit() // Cierra el formulario de edición
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}