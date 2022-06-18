package com.example.chucknorries.data.mapper

import com.example.chucknorries.data.api.dto.JokesDTO
import com.example.chucknorries.data.mapper.base.BaseMapper
import com.example.chucknorries.domain.entities.JokesEntity

class JokeEntityMapper:BaseMapper<JokesDTO,JokesEntity> {
    override fun mapFromModel(model: JokesDTO): JokesEntity {
        return JokesEntity(
            model.categories,
            model.id,
            model.value
        )
    }

}