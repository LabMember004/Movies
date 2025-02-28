package com.example.domain

import com.example.domain.repository.MovieRepository
import com.example.domain.useCase.GetMoviesSectionsUseCase
import com.example.domain.entity.SectionResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMoviesSectionsUseCaseTest {

    private val movieRepository: MovieRepository = mockk()

    private lateinit var getMoviesSectionsUseCase: GetMoviesSectionsUseCase

    @Before
    fun setUp() {
        getMoviesSectionsUseCase = GetMoviesSectionsUseCase(movieRepository)
    }

    @Test
    fun `invoke should return sections from repository`() = runBlocking {

        val expectedResponse = SectionResponse(
            highlighted = mockk(),
            sections = listOf(mockk())
        )
        coEvery { movieRepository.getSections() } returns expectedResponse


        val result = getMoviesSectionsUseCase()


        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { movieRepository.getSections() }
    }


}
