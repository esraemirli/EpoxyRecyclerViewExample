package com.example.rickandmortyapp.model.repository

import com.example.rickandmortyapp.model.entity.Character
import com.example.rickandmortyapp.model.remote.CharacterService
import com.example.rickandmortyapp.ui.home.epoxy.CharacterMapper
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.RuntimeException
import javax.inject.Inject

@ActivityRetainedScoped
class CharacterRepository @Inject constructor(
    private val service: CharacterService,
    private val mapper: CharacterMapper
) {

    suspend fun getCharacterList(): Flow<Result<List<Character>>> =
        service.getCharacterList().map { result ->
            if (result.isSuccess)
                Result.success(mapper(result.getOrNull()!!))
            else
                Result.failure(result.exceptionOrNull() ?: RuntimeException("Fail"))
        }

    suspend fun getCharacterById(id : Int): Flow<Result<Character>> =
        service.getCharacterById(id).map { result ->
            if (result.isSuccess)
                Result.success(result.getOrNull()!!)
            else
                Result.failure(result.exceptionOrNull() ?: RuntimeException("Fail"))
        }
}