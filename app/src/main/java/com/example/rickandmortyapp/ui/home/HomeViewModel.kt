package com.example.rickandmortyapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.model.remote.NetworkResponse
import com.example.rickandmortyapp.model.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    fun fetchCharacters() = viewModelScope.launch {
        characterRepository
            .getCharacterList().collect {
                if (it.status == NetworkResponse.Status.SUCCESS)
                    Log.v("HomeViewModel", it.data?.results.toString())
                else
                    Log.v("HomeViewModel", "Error: ${it.error?.message}")
            }
    }

}