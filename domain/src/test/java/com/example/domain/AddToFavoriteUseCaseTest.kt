//package com.example.domain
//
//import com.example.domain.entity.FavoriteRequest
//import com.example.domain.repository.MovieRepository
//import com.example.domain.useCase.AddToFavoriteUseCase
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.runBlocking
//import org.junit.Before
//import org.junit.Test
//import kotlin.math.exp
//
//class AddToFavoriteUseCaseTest {
//
//    private val movieRepository: MovieRepository = mockk()
//    private lateinit var addToFavoriteUseCase: AddToFavoriteUseCase
//
//
//    @Before
//    fun setUp() {
//        addToFavoriteUseCase = AddToFavoriteUseCase(movieRepository)
//    }
//
//    @Test
//    fun `invoke should return movies favorites from repository`(): Unit = runBlocking {
//        val expectedResponse = FavoriteRequest(
//            movieId = "1"
//        )
//        val token = "valid_token"
//        coEvery { movieRepository.addToFavorites(token, expectedResponse) } returns Result.success(Unit)
//
//        val result = addToFavoriteUseCase.invoke(token , expectedResponse)
//
//        coVerify { movieRepository.addToFavorites(token, expectedResponse) }
//        assert(result.isSuccess)
//    }
//
//    @Test
//
//    fun `invoke should return failure if repository throws an error`() = runBlocking {
//        val expectedResponse = FavoriteRequest(movieId = "1")
//        val token = "valid_token"
//
//        // Mocking repository method to throw an error
//        coEvery { movieRepository.addToFavorites(token, expectedResponse) } returns Result.failure(Exception("Error"))
//
//        // Call the use case
//        val result = addToFavoriteUseCase.invoke(token, expectedResponse)
//
//        // Verify the repository was called
//        coVerify { movieRepository.addToFavorites(token, expectedResponse) }
//
//        // Verify that the result is a failure
//        assert(result.isFailure)
//    }
//}