package com.example.data.netwok

import com.example.data.model.AllMoviesResponse
import com.example.data.model.LoginRequestDTO
import com.example.data.model.LoginResponseDTO
import com.example.data.model.RegisterRequestDTO
import com.example.data.model.RegisterResponseDTO
import com.example.data.model.SectionResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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




}