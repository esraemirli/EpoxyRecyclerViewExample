package com.example.rickandmortyapp.ui.home

import androidx.lifecycle.*
import com.example.rickandmortyapp.model.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    var loader = MutableLiveData<Boolean>()

    var characterList = liveData {
        loader.postValue(true)
        emitSource(repository.getCharacterList().onEach {
            loader.postValue(false)
        }.asLiveData())
    }

}