package com.example.rickandmortyapp.model.entity


import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("created")
    val created: String? = null,
    @SerializedName("episode")
    val episode: List<String>? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: Origin? = null,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: String? = null
)