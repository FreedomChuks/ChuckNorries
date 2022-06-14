package com.example.chucknorries.data.api.dto

data class ErrorResponse(
    val error: String,
    val message: String,
    val path: String,
    val status: Int,
    val timestamp: String
)