package com.example.chucknorries.data.remote.model

data class JokesResponse(
    val categories: List<String>,
    val id: String,
    val value: String
)