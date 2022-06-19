package com.example.chucknorries.data.datasource.cache.mapper

import com.example.chucknorries.data.local.entities.JokesCacheEntity
import com.example.chucknorries.domain.entities.JokesEntity

/**
 * JokeCacheEntity Mapper.
 * Maps JokeCacheEntity (Database Entity) -> JokeEntity (Domain)
 *  Maps JokeEntity (Domain) -> JokeCacheEntity (Database Entity)
 */

fun JokesEntity.mapToDatabaseEntity() = JokesCacheEntity(
    id=id,
    value = value,
    categories = categories.ifEmpty { listOf("uncategorized") }
)

//Database Entity to Domain Model
fun JokesCacheEntity.mapToDatabaseEntity() = JokesEntity(
    id=id,
    value = value,
    categories = categories
)