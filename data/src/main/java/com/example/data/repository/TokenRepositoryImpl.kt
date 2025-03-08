package com.example.data.repository

import android.content.Context
import com.example.data.TokenManager
import com.example.domain.TokenRepositoryDomain
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
        tokenFlow.value = token // ✅ Update Flow with new token
    }

    override fun getToken(): Flow<String?> = flow {
        emit(TokenManager.getToken(context)) // ✅ Convert stored token to Flow
    }
}
