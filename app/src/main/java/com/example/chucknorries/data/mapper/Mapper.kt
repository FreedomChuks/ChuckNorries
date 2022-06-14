package com.example.chucknorries.data.mapper

import com.example.chucknorries.data.api.dto.Jokes
import com.example.chucknorries.data.api.dto.JokesList
import com.example.chucknorries.data.local.entities.ChuckNorrisEntity
import com.example.chucknorries.domain.JokesEntity
import com.example.chucknorries.domain.JokesListEntity

//DTO to JokeEntity
fun Jokes.toEntity() = JokesEntity(
    id=id,
    value = value,
    categories = categories,
)

fun JokesList.mapToEntity() = JokesListEntity(
    result = result.map { it.toEntity() }

)

//Database Entity to Domain Model
fun JokesEntity.mapToDomain() = ChuckNorrisEntity(
    id=id,
    value = value,
    categories = categories
)

//Database Entity to Domain Model
fun ChuckNorrisEntity.mapToDatabaseEntity() = JokesEntity(
    id=id,
    value = value,
    categories = categories
)