package com.example.deezertx.ui.fragment.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.deezertx.TestCoroutineRule
import com.example.deezertx.getOrAwaitValue
import com.example.deezertx.mockAlbums
import com.example.deezertx.model.Albums
import com.example.deezertx.repository.albums.AlbumsRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
internal class AlbumsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    lateinit var albumsViewModel: AlbumsViewModel

    @Mock
    lateinit var repository: AlbumsRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        albumsViewModel = AlbumsViewModel(repository)
    }

    @Test
    fun givenServerOk_whenFetch_shouldReturnSuccess() {

        runBlocking {
            Mockito.`when`(repository.fetchAlbums(0))
                .thenReturn(ApiResponse.Success(Response.success(mockAlbums)))

        }
        albumsViewModel.getAllAlbums(0)
        val result = albumsViewModel.albums.getOrAwaitValue()

        assertEquals(mockAlbums, result)
        assertEquals(2, result?.albums?.size)
    }


    @Test
    fun givenServerKO_whenFetch_shouldReturnUnauthorizedError() {
        val errorResponse =
            "{\n" +
                    "  \"type\": \"error\",\n" +
                    "  \"message\": \"What you were looking for isn't here.\"\n" + "}"

        val errorResponseBody: ResponseBody =
            errorResponse.toResponseBody("application/json".toMediaTypeOrNull())

        val apiResponse: ApiResponse.Failure.Error<Albums> =
            ApiResponse.Failure.Error(Response.error(401, errorResponseBody))

        runBlocking {
            Mockito.`when`(repository.fetchAlbums(0))
                .thenReturn(apiResponse)
        }
        albumsViewModel.getAllAlbums(0)
        val error = albumsViewModel.error.getOrAwaitValue()
        val formatApiResponse =
            "${apiResponse.statusCode}(${apiResponse.statusCode.code}): ${apiResponse.message()}"
        assertEquals(error, formatApiResponse)
    }

    @Test
    fun givenServerKO_whenFetch_shouldReturnExceptionError() {
        val exception = Exception("SomeException")
        val apiResponse: ApiResponse.Failure.Exception<Albums> = ApiResponse.error(exception)

        runBlocking {
            Mockito.`when`(repository.fetchAlbums(0))
                .thenReturn(apiResponse)
        }
        albumsViewModel.getAllAlbums(0)
        val result = albumsViewModel.error.getOrAwaitValue()

        assertEquals(result, (apiResponse.toString()))
    }

}