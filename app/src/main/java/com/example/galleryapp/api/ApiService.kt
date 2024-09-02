package com.example.galleryapp.api

import com.example.galleryapp.data.ResultCharacter
import com.example.galleryapp.data.RickAndMortyInfo
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("character/")
    suspend fun getNewCharacters(
        @Query("name") name: String?,
        @Query("page") page: Int? = null,
    ): Response<RickAndMortyInfo>

    @GET("character/")
    suspend fun getPopularCharacters(
        @Query("name") name: String?,
        @Query("page") page: Int? = null,
    ): Response<RickAndMortyInfo>
}