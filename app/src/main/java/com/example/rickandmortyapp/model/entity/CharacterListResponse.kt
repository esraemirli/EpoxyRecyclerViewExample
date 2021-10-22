package com.example.rickandmortyapp.model.entity


import com.google.gson.annotations.SerializedName

data class CharacterListResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Character>
)