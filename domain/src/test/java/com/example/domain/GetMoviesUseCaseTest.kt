package com.example.domain

import com.example.domain.entity.MoviesResponse
import com.example.domain.repository.MovieRepository
import com.example.domain.useCase.GetMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMoviesUseCaseTest {
    
    private val movieRepository : MovieRepository = mockk()
    private lateinit var getMoviesUseCase: GetMoviesUseCase 
    
    @Before
    fun setUp() {
        getMoviesUseCase = GetMoviesUseCase(movieRepository)
    }
    @Test
    fun `invoke should return movies from repository`(): Unit = runBlocking {
        val expectedResponse = MoviesResponse(
            totalDocs = 100,
            totalPages = 3,
            currentPage = 1,
            data = listOf(mockk())
        )
        coEvery { movieRepository.getMovies(
            page = 1,
            genres = "Action",
            releaseYear = 2024,
            query = "Avengers"
        ) } returns expectedResponse

        val result = getMoviesUseCase(1, "Action", 2024, "Avengers")


        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { movieRepository.getMovies(1, "Action", 2024, "Avengers") }
    }
}