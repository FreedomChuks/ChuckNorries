package com.example.chucknorries.data.mapper

import com.example.chucknorries.data.api.dto.Jokes
import com.example.chucknorries.data.api.dto.JokesList
import com.example.chucknorries.domain.JokesEntity
import com.example.chucknorries.domain.JokesListEntity

fun Jokes.toEntity() = JokesEntity(
    id=id,
    value = value,
    categories = categories,
)

fun JokesList.mapToEntity() = JokesListEntity(
    result = result.map { it.toEntity() }
)