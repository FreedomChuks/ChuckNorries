package com.example.chucknorries.data.mapper.base

interface BaseMapper<in M,out E> {

    fun mapFromModel(model:M):E

    fun mapModelList(model: List<M>):List<E>{
        return model.mapTo(mutableListOf(),::mapFromModel)
    }
}