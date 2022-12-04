package com.pawan.sampleproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pawan.sampleproject.data.Repository
import com.pawan.sampleproject.getOrAwaitValue
import com.pawan.sampleproject.model.DogResponse
import com.pawan.sampleproject.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: Repository

    @Before
    fun setUp() {

        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getDogs() = runTest{
        Mockito.`when`(repository.getDog()).thenReturn(flowOf(NetworkResult.Success(DogResponse("", ""))))
        val sut = MainViewModel(repository)
        sut.fetchDogResponse()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.response.getOrAwaitValue()
        assertEquals("", result.data!!.status)
        assertEquals("", result.data!!.message)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getDogsFail() = runTest {

        Mockito.`when`(repository.getDog()).thenReturn(flowOf(NetworkResult.Error("something went wrong")))
        val sut = MainViewModel(repository)
        sut.fetchDogResponse()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.response.getOrAwaitValue()
        assertEquals(true, result is NetworkResult.Error)
        assertEquals("something went wrong", result.message)
    }

    @After
    fun tearDown() {
    }
}