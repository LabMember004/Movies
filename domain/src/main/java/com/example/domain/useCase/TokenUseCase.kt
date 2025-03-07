package com.example.domain.useCase


import android.util.Log
import com.example.domain.TokenRepositoryDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TokenUseCase @Inject constructor(
    private val tokenRepositoryDomain: TokenRepositoryDomain
) {
    suspend fun saveToken(token: String) {
        Log.d("TokenUseCase", "Saving token: $token")
        tokenRepositoryDomain.saveToken(token)
    }

    fun getToken(): Flow<String?> {
        return tokenRepositoryDomain.getToken().onEach { token ->
            Log.d("TokenUseCase", "Retrieved token: $token")
        }
    }
}

