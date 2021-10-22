package com.example.rickandmortyapp.model.repository

import com.example.rickandmortyapp.model.entity.CharacterListResponse
import com.example.rickandmortyapp.util.BaseDataSource
import com.example.rickandmortyapp.model.remote.CharacterService
import com.example.rickandmortyapp.model.remote.NetworkResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class CharacterRepository @Inject constructor(private val service: CharacterService) :
    BaseDataSource() {

    suspend fun getCharacterList(): Flow<NetworkResponse<CharacterListResponse>> = flow {
        emit(getResult {
            service.getCharacterList()
        })
    }.flowOn(Dispatchers.IO)

}