package com.example.rickandmortyapp.model.entity


import com.google.gson.annotations.SerializedName

data class RickAndMortyResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val characterList: List<Character>
)