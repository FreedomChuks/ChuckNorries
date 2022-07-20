package com.example.chucknorries.data.remote.model

data class JokesResponse(
    val categories: List<String>,
    val id: String?=null,
    val value: String?=null
)