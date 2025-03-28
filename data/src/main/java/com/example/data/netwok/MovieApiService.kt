package com.example.data.netwok

import com.example.data.model.AllMovies
import com.example.data.model.AllMoviesResponse
import com.example.data.model.FavoriteRequestDTO
import com.example.data.model.LoginRequestDTO
import com.example.data.model.LoginResponseDTO
import com.example.data.model.RegisterRequestDTO
import com.example.data.model.RegisterResponseDTO
import com.example.data.model.SectionResponseDTO
import com.example.data.model.UpdateEmailRequestDTO
import com.example.data.model.UpdateEmailResponseDTO
import com.example.data.model.UpdatePasswordRequestDTO
import com.example.data.model.UpdatePasswordResponseDTO
import com.example.domain.entity.Movies
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
@GET("movies")
suspend fun getMovies(
    @Query("page") page: Int,
    @Query("genre") genre: String? = null,
    @Query("releaseYear") releaseYear: Int? = null,
    @Query("query") query: String? = null
): AllMoviesResponse

@GET("movies/sections")
suspend fun getSections(): SectionResponseDTO

@POST("auth/register")
suspend fun register(
    @Body registerRequestDTO: RegisterRequestDTO
): RegisterResponseDTO

@POST("auth/login")
suspend fun login(@Body request : LoginRequestDTO): LoginResponseDTO


@POST("favorites")
suspend fun addToFavorites(
    @Body request: FavoriteRequestDTO
): Response<Unit>

@GET("favorites")
suspend fun getFavorites(): Response<List<AllMovies>>
@DELETE("favorites/{favoriteId}")

suspend fun deleteFavorite(
        @Path("favoriteId") favoriteId: String,
        ): Response<ResponseBody>

@PATCH("me")
suspend fun updateEmail(
    @Body request: UpdateEmailRequestDTO
): Response<UpdateEmailResponseDTO>

@PATCH("me/password")
suspend fun updatePassword(
    @Body request: UpdatePasswordRequestDTO
): Response<UpdatePasswordResponseDTO>

@DELETE("me")
suspend fun deleteProfile(

): Response<Unit>


}