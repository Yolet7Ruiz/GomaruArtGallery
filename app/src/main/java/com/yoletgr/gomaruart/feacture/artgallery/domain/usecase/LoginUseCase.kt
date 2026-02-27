package com.yoletgr.gomaruart.feature.artgallery.domain.usecase

import com.yoletgr.gomaruart.feature.artgallery.domain.repositories.ArtRepository

class LoginUseCase(private val repository: ArtRepository) {
    suspend operator fun invoke(username: String, password: String): Boolean {
        return if (username.isNotBlank() && password.isNotBlank()) {
            repository.login(username, password)
        } else false
    }
}