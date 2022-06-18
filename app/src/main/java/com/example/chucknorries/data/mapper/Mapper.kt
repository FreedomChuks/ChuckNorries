package com.example.chucknorries.data.mapper

import com.example.chucknorries.data.api.dto.JokesDTO
import com.example.chucknorries.data.api.dto.JokesListDTO
import com.example.chucknorries.data.local.entities.JokesDBEntity
import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.domain.entities.JokesListEntity

//DTO to JokeEntity
fun JokesDTO.toEntity() = JokesEntity(
    id=id,
    value = value,
    categories = categories,
)

fun JokesListDTO.mapToEntity() = JokesListEntity(
    result = result.map { it.toEntity() }

)

//Database Entity to Domain Model
fun JokesEntity.mapToDatabaseEntity() = JokesDBEntity(
    id=id,
    value = value,
    categories = categories.ifEmpty { listOf("uncategorized") }
)

//Database Entity to Domain Model
fun JokesDBEntity.mapToDatabaseEntity() = JokesEntity(
    id=id,
    value = value,
    categories = categories
)