package com.example.rickandmortyapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.rickandmortyapp.model.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    var loader = MutableLiveData<Boolean>()
    var characterId : Int = -1

    var characterDetail = liveData {
        loader.postValue(true)
        emitSource(repository.getCharacterById(characterId).onEach {
            loader.postValue(false)
        }.asLiveData())
    }

}