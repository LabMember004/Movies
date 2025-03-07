package com.example.domain

import kotlinx.coroutines.flow.Flow

interface TokenRepositoryDomain {
    suspend fun saveToken(token: String)
    fun getToken(): Flow<String?>
    suspend fun clearToken()
}