package com.example.rickandmortyapp.util

data class StateError(
    var exception: Exception? = null,
    var message: String? = null
)