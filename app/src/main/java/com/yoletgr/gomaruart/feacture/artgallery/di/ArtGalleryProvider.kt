package com.yoletgr.gomaruart.feature.artgallery.di

import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.api.ArtApi
import com.yoletgr.gomaruart.feature.artgallery.data.repositories.ArtGalleryRepositoryImpl
import com.yoletgr.gomaruart.feature.artgallery.domain.repositories.ArtRepository
import com.yoletgr.gomaruart.feature.artgallery.domain.usecase.*
import com.yoletgr.gomaruart.feature.artgallery.presentation.viewmodels.ArtGalleryViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ArtGalleryProvider {
    private const val BASE_URL = "http://192.168.100.27:8080/" // Cambiar por la IP

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val artApi: ArtApi by lazy {
        retrofit.create(ArtApi::class.java)
    }

    private val artRepository: ArtRepository by lazy {
        ArtGalleryRepositoryImpl(artApi)
    }

    val getArtUseCase by lazy { GetArtUseCase(artRepository) }
    val addArtUseCase by lazy { AddArtUseCase(artRepository) }
    val deleteArtUseCase by lazy { DeleteArtUseCase(artRepository) }
    val updateArtUseCase by lazy { UpdateArtUseCase(artRepository) }
    val loginUseCase by lazy { LoginUseCase(artRepository) }

    val factory by lazy {
        ArtGalleryViewModelFactory(
            getArtUseCase = getArtUseCase,
            addArtUseCase = addArtUseCase,
            deleteArtUseCase = deleteArtUseCase,
            updateArtUseCase = updateArtUseCase
        )
    }
}