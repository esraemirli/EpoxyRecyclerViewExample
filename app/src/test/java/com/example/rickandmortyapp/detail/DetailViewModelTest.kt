package com.example.rickandmortyapp.detail

import com.example.rickandmortyapp.model.entity.Character
import com.example.rickandmortyapp.model.repository.CharacterRepository
import com.example.rickandmortyapp.ui.detail.CharacterDetailViewModel
import com.example.rickandmortyapp.util.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class DetailViewModelTest : BaseUnitTest() {
    private val repository: CharacterRepository = mock()
    private val character = mock<Character>()
    private val expected = Result.success(character)
    private val exception = RuntimeException("Something went wrong!")

    @Test
    fun `emits character detail success`(): Unit = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        val actual = viewModel.characterDetail.getValueForTest()

        verify(repository, times(1)).getCharacterById(-1)
        assert(expected == actual)
    }

    @Test
    fun `emits character detail failed`() {
        val viewModel = mockFailureCase()
        val actual = viewModel.characterDetail.getValueForTest()!!.exceptionOrNull()
        assert(exception == actual)
    }

    @Test
    fun `show spinner while loading`(): Unit = runBlocking {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.characterDetail.getValueForTest()
            assert(true == values[0])
        }
    }

    @Test
    fun `close loader after character load`(): Unit = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.characterDetail.getValueForTest()
            assert(false == values.last())
        }
    }

    @Test
    fun `close loader after error`(): Unit = runBlockingTest {
        val viewModel = mockFailureCase()
        viewModel.loader.captureValues {
            viewModel.characterDetail.getValueForTest()
            assert(false == values.last())
        }
    }

    private suspend fun mockSuccessfulCase(): CharacterDetailViewModel {
        runBlockingTest {
            whenever(repository.getCharacterById(Mockito.anyInt())).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return CharacterDetailViewModel(repository)
    }

    private fun mockFailureCase(): CharacterDetailViewModel {
        runBlockingTest {
            whenever(repository.getCharacterById(Mockito.anyInt())).thenReturn(
                flow {
                    emit(Result.failure<Character>(exception))
                }
            )
        }
        return CharacterDetailViewModel(repository)
    }

}