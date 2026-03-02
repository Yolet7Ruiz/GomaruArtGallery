    package com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.api

    import com.yoletgr.gomaruart.feature.artgallery.data.datasources.remote.models.ArtDto
    import retrofit2.Response
    import retrofit2.http.*

    interface ArtApi {
        @GET("obras")
        suspend fun fetchAll(): Response<List<ArtDto>>

        @GET("obras/{id}")
        suspend fun getById(@Path("id") id: Int): Response<ArtDto>

        @GET("obras/categoria/{idCategoria}")
        suspend fun getByCategory(@Path("idCategoria") idCategoria: Int): Response<List<ArtDto>>

        @POST("obras")
        suspend fun create(@Body dto: ArtDto): Response<ArtDto>

        @PUT("obras/{id}")
        suspend fun update(@Path("id") id: Int, @Body dto: ArtDto): Response<ArtDto>

        @DELETE("obras/{id}")
        suspend fun delete(@Path("id") id: Int): Response<Unit>
    }