package com.example.rickandmortyapp.model

import com.example.rickandmortyapp.model.entity.Character
import com.example.rickandmortyapp.model.remote.CharacterService
import com.example.rickandmortyapp.model.repository.CharacterRepository
import com.example.rickandmortyapp.ui.home.epoxy.CharacterMapper
import com.example.rickandmortyapp.util.BaseUnitTest
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class CharacterRepositoryTest : BaseUnitTest() {
    private val service: CharacterService = mock()
    private val characterList = mock<List<Character>>()
    private val character = mock<Character>()
    private val mapper: CharacterMapper = mock()
    private val exception = RuntimeException("Something went wrong!")

    @Test
    fun `emit character list from service`(): Unit = runBlocking {
        val repository = mockCharacterListSuccessCase()
        val actual = repository.getCharacterList().first().getOrNull()

        assert(actual == characterList)
        verify(service, times(1)).getCharacterList()
    }

    @Test
    fun `delegate business logic to mapper`(): Unit = runBlockingTest {
        val repository = mockCharacterListSuccessCase()

        repository.getCharacterList().first()
        verify(mapper, times(1)).invoke(characterList)
    }

    @Test
    fun `character list propagate error`(): Unit = runBlockingTest {
        val repository = mockCharacterListFailureCase()
        assert(exception == repository.getCharacterList().first().exceptionOrNull())
    }

    @Test
    fun `emit character by id from service`(): Unit = runBlocking {
        val id = 5
        val repository = mockCharacterSuccessCase()
        val actual = repository.getCharacterById(id).first().getOrNull()

        assert(actual == character)
        verify(service, times(1)).getCharacterById(id)
    }

    @Test
    fun `character by id propagate error`(): Unit = runBlocking {
        val repository = mockCharacterFailureCase()
        assert(exception == repository.getCharacterById(5).first().exceptionOrNull())
    }

    private suspend fun mockCharacterListFailureCase(): CharacterRepository {
        whenever(service.getCharacterList()).thenReturn(
            flow {
                emit(Result.failure<List<Character>>(exception))
            }
        )

        return CharacterRepository(service, mapper)
    }

    private suspend fun mockCharacterListSuccessCase(): CharacterRepository {
        whenever(service.getCharacterList()).thenReturn(
            flow {
                emit(Result.success(characterList))
            }
        )

        whenever(mapper.invoke(characterList)).thenReturn(characterList)
        return CharacterRepository(service, mapper)
    }

    private suspend fun mockCharacterSuccessCase(): CharacterRepository {
        whenever(service.getCharacterById(Mockito.anyInt())).thenReturn(
            flow {
                emit(Result.success(character))
            }
        )

        return CharacterRepository(service, mapper)
    }

    private suspend fun mockCharacterFailureCase(): CharacterRepository {
        whenever(service.getCharacterById(Mockito.anyInt())).thenReturn(
            flow {
                emit(Result.failure<Character>(exception))
            }
        )

        return CharacterRepository(service, mapper)
    }
}