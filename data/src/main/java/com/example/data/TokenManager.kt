package com.example.data


import android.content.Context
import android.util.Log

object TokenManager {
    private const val PREF_NAME = "auth_prefs"
    private const val TOKEN_KEY = "auth_token"

    fun saveToken(context: Context, token: String?) {
        if (token.isNullOrEmpty()) {
            Log.e("TokenManager", "Attempted to save a null/empty token! Skipping...")
            return
        }
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(TOKEN_KEY, token).apply()
        Log.d("TokenManager", "Token saved successfully: $token")
    }


    fun getToken(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val token = prefs.getString(TOKEN_KEY, null)
        Log.d("TokenManager", "Retrieved Token: $token")
        return token
    }

}
