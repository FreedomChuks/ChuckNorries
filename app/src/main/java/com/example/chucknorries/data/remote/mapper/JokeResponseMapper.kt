package com.example.chucknorries.data.remote.mapper

import com.example.chucknorries.data.remote.model.JokesListResponse
import com.example.chucknorries.data.remote.model.JokesResponse
import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.domain.entities.JokesListEntity

/**
 * JokeRemoteResponse Mapper.
 * Maps JokeResponse (DTO) -> JokeEntity (Domain)
 *  Maps JokeListResponse (DTO) -> JokeListEntity (Domain)
 */
internal fun JokesResponse.toJokeEntity() = JokesEntity(
    id=id,
    value = value,
    categories = categories,
)

internal fun JokesListResponse.toJokeListEntity() = JokesListEntity(
    result = result.map { it.toJokeEntity() }
)