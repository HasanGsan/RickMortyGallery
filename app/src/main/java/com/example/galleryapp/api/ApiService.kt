package com.example.galleryapp.api

import com.example.galleryapp.data.RickAndMortyInfo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("character")
    fun getCharacters(): Call<RickAndMortyInfo>
}