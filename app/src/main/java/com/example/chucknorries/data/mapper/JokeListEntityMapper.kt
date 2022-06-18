package com.example.chucknorries.data.mapper

import com.example.chucknorries.data.api.dto.JokesListDTO
import com.example.chucknorries.data.mapper.base.BaseMapper
import com.example.chucknorries.domain.entities.JokesListEntity

class JokeListEntityMapper:BaseMapper<JokesListDTO,JokesListEntity> {
    override fun mapFromModel(model: JokesListDTO): JokesListEntity {
        return JokesListEntity(
            result = model.mapToEntity().result
        )
    }
}