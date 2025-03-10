package com.example.data.repository

import android.content.Context
import com.example.data.TokenManager
import com.example.domain.repository.TokenRepositoryDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val context: Context
) : TokenRepositoryDomain {

    private val tokenFlow = MutableStateFlow<String?>(null)

    override suspend fun saveToken(token: String) {
        TokenManager.saveToken(context, token)
        tokenFlow.value = token
    }

    override fun getToken(): Flow<String?> = flow {
        emit(TokenManager.getToken(context))
    }
}
