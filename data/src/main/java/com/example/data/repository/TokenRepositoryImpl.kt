package com.example.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.TokenRepositoryDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class TokenRepositoryImpl(private val context: Context) : TokenRepositoryDomain {
    private val TOKEN_KEY = stringPreferencesKey("auth_token")

    override suspend fun saveToken(token: String) {
        Log.d("TokenRepositoryImpl", "Saving token to SharedPreferences: $token")

        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    override fun getToken(): Flow<String?> = context.dataStore.data.map { prefs ->

        prefs[TOKEN_KEY]
    }

    override suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }
}
