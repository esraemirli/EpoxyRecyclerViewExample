package com.example.rickandmortyapp.model.remote

import com.example.rickandmortyapp.model.entity.Character
import com.example.rickandmortyapp.model.entity.RickAndMortyResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterAPI {
    @GET("character")
    suspend fun getCharacterList(): RickAndMortyResponse

    @GET("character/{charId}")
    suspend fun getCharacterById(
        @Path("charId") charId: Int,
    ): Character
}