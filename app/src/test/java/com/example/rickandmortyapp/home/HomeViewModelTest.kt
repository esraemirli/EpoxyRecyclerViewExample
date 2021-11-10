package com.example.rickandmortyapp.home

import com.example.rickandmortyapp.model.entity.Character
import com.example.rickandmortyapp.model.repository.CharacterRepository
import com.example.rickandmortyapp.ui.home.HomeViewModel
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
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class HomeViewModelTest : BaseUnitTest() {
    private val repository: CharacterRepository = mock()
    private val characterList = mock<List<Character>>()
    private val expected = Result.success(characterList)
    private val exception = RuntimeException("Something went wrong!")

    @Test
    fun `emits character list success`(): Unit = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        val actual = viewModel.characterList.getValueForTest()

        verify(repository, times(1)).getCharacterList()
        assert(expected == actual)
    }

    @Test
    fun `emits character list failed`() {
        val viewModel = mockFailureCase()
        val actual = viewModel.characterList.getValueForTest()!!.exceptionOrNull()
        assert(exception == actual)
    }

    @Test
    fun `show spinner while loading`(): Unit = runBlocking {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.characterList.getValueForTest()
            assert(true == values[0])
        }
    }

    @Test
    fun `close loader after character list load`(): Unit = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.characterList.getValueForTest()
            assert(false == values.last())
        }
    }

    @Test
    fun `close loader after error`(): Unit = runBlockingTest {
        val viewModel = mockFailureCase()
        viewModel.loader.captureValues {
            viewModel.characterList.getValueForTest()
            assert(false == values.last())
        }
    }

    private fun mockSuccessfulCase(): HomeViewModel {
        runBlockingTest {
            whenever(repository.getCharacterList()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return HomeViewModel(repository)
    }

    private fun mockFailureCase(): HomeViewModel {
        runBlockingTest {
            whenever(repository.getCharacterList()).thenReturn(
                flow {
                    emit(Result.failure<List<Character>>(exception))
                }
            )
        }
        return HomeViewModel(repository)
    }

}