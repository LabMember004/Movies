//package com.example.domain
//
//import com.example.domain.repository.MovieRepository
//import com.example.domain.useCase.DeleteFavoriteUseCase
//import io.mockk.coEvery
//import io.mockk.mockk
//import junit.framework.TestCase.assertEquals
//import junit.framework.TestCase.assertTrue
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.Before
//import org.junit.Test
//
//@ExperimentalCoroutinesApi
//class DeleteFavoriteUseCaseTest {
//    private lateinit var deleteFavoriteUseCase: DeleteFavoriteUseCase
//    private val movieRepository: MovieRepository = mockk()
//
//    @Before
//    fun setup() {
//        deleteFavoriteUseCase = DeleteFavoriteUseCase(movieRepository)
//    }
//
//    @Test
//    fun `invoke should return success when deletion is successful`() = runBlockingTest {
//        coEvery { movieRepository.deleteFavorite(any(), any()) } returns Result.success(Unit)
//
//        val result = deleteFavoriteUseCase("token", "favoriteId")
//
//        assertTrue(result.isSuccess)
//    }
//
//    @Test
//    fun `invoke should return error when deletion fails`() = runBlockingTest {
//        coEvery { movieRepository.deleteFavorite(any(), any()) } returns Result.failure(Exception("Deletion error"))
//
//        val result = deleteFavoriteUseCase("token", "favoriteId")
//
//        assertTrue(result.isFailure)
//        assertEquals("Deletion error", result.exceptionOrNull()?.message)
//    }
//}
