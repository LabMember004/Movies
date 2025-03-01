package com.example.domain.repository

import android.telecom.Call
import com.example.domain.entity.MoviesResponse
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse
import com.example.domain.entity.SectionResponse


interface MovieRepository {
    suspend fun getMovies(page: Int, genres: String? , releaseYear:Int? , query: String?): MoviesResponse

    suspend fun getSections(): SectionResponse

     suspend fun register(registerRequest: RegisterRequest): Result <RegisterResponse>
}