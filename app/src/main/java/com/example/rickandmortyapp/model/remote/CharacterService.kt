package com.example.rickandmortyapp.model.remote

import com.example.rickandmortyapp.model.entity.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class CharacterService @Inject constructor(
    private val api: CharacterAPI
) {

    suspend fun getCharacterList(): Flow<Result<List<Character>>> {
        return flow {
            emit(Result.success(api.getCharacterList().characterList))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong!${it.message}")))
        }
    }


}