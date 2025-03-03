package com.example.domain

import com.example.domain.entity.Movies
import com.example.domain.repository.MovieRepository
import com.example.domain.useCase.GetFavoriteUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFavoritesUseCaseTest {
    private lateinit var getFavoriteUseCase: GetFavoriteUseCase
    private val movieRepository: MovieRepository = mockk()

    @Before
    fun setup() {
        getFavoriteUseCase = GetFavoriteUseCase(movieRepository)
    }

    @Test
    fun `invoke should return list of movies when successful`() = runBlockingTest {
        val movies = listOf(Movies("1", "Movie 1", 2022, "poster.jpg", 8.0, 142, listOf("Action"), "trailer_url"))
        coEvery { movieRepository.getFavorites(any()) } returns Result.success(movies)

        val result = getFavoriteUseCase("token")

        assertTrue(result.isSuccess)
        assertEquals(movies, result.getOrNull())
    }

    @Test
    fun `invoke should return error when repository fails`() = runBlockingTest {
        coEvery { movieRepository.getFavorites(any()) } returns Result.failure(Exception("Network error"))

        val result = getFavoriteUseCase("token")

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}
