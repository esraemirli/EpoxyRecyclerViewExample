package com.example.rickandmortyapp.model

import com.example.rickandmortyapp.model.entity.Character
import com.example.rickandmortyapp.model.entity.RickAndMortyResponse
import com.example.rickandmortyapp.model.remote.CharacterAPI
import com.example.rickandmortyapp.model.remote.CharacterService
import com.example.rickandmortyapp.util.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class CharacterServiceTest : BaseUnitTest() {
    private val api: CharacterAPI = mock()
    private val characterResponse = mock<RickAndMortyResponse>()
    private val character = mock<Character>()
    private val exception = RuntimeException("Something went wrong!")

    @Test
    fun `emit character list from api`(): Unit = runBlockingTest {
        val service = mockCharacterListSuccessCase()
        val actual = service.getCharacterList().first()
        assert(actual == Result.success(characterResponse.characterList))
        verify(api, times(1)).getCharacterList()
    }

    @Test
    fun `emit error result when network fail`(): Unit = runBlockingTest {
        val service = mockCharacterListFailureCase()
        val actual = service.getCharacterList().first()
        assert(actual.exceptionOrNull()?.message == exception.message)
    }

    @Test
    fun `emit character by id from api`(): Unit = runBlockingTest {
        val id = 5
        val service = mockCharacterSuccessCase()
        val actual = service.getCharacterById(id).first()

        assert(actual == Result.success(character))
        verify(api, times(1)).getCharacterById(id)
    }

    @Test
    fun `emit character by id error result when network fail`(): Unit = runBlockingTest {
        val id = 5
        val service = mockCharacterFailureCase()
        val actual = service.getCharacterById(id).first()
        assert(actual.exceptionOrNull()?.message == exception.message)
    }

    private suspend fun mockCharacterListSuccessCase(): CharacterService {
        whenever(api.getCharacterList()).thenReturn(characterResponse)
        return CharacterService(api)
    }

    private suspend fun mockCharacterListFailureCase(): CharacterService {
        whenever(api.getCharacterList()).thenThrow(exception)
        return CharacterService(api)
    }

    private suspend fun mockCharacterSuccessCase(): CharacterService {
        whenever(api.getCharacterById(Mockito.anyInt())).thenReturn(character)
        return CharacterService(api)
    }

    private suspend fun mockCharacterFailureCase(): CharacterService {
        whenever(api.getCharacterById(Mockito.anyInt())).thenThrow(exception)
        return CharacterService(api)
    }


}