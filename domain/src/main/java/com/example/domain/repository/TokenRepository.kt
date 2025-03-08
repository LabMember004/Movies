package com.example.domain.repository

interface TokenRepository {
    fun getToken(): String?
}