package com.example.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepositoryDomain {
    suspend fun saveToken(token: String)
    fun getToken(): Flow<String?>

}