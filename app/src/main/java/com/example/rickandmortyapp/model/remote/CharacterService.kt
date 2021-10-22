package com.example.rickandmortyapp.model.remote

import com.example.rickandmortyapp.model.entity.CharacterListResponse
import retrofit2.Response
import retrofit2.http.GET

interface CharacterService {
    @GET("character")
    suspend fun getCharacterList() : Response<CharacterListResponse>



}