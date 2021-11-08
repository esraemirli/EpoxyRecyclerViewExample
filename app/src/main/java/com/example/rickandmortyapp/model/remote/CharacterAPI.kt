package com.example.rickandmortyapp.model.remote

import com.example.rickandmortyapp.model.entity.RickAndMortyResponse
import retrofit2.http.GET

interface CharacterAPI {
    @GET("character")
    suspend fun getCharacterList(): RickAndMortyResponse
}