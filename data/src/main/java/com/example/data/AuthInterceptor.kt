package com.example.data

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = TokenManager.getToken(context)
        Log.d("AuthInterceptor", "Retrieved Token: $token")

        val request: Request = chain.request().newBuilder()
            .apply {
                token?.let { header("Authorization", "Bearer $it") }
            }
            .build()
        return chain.proceed(request)
    }
}