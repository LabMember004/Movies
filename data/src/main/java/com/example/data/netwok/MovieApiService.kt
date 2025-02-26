package com.example.data.netwok

import com.example.data.model.AllMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
@GET("movies")
suspend fun getMovies(
    @Query("page") page: Int,
    @Query("genre") genre: String? = null,
    @Query("releaseYear") releaseYear: Int? = null,
    @Query("query") query: String? = null
): AllMoviesResponse

}