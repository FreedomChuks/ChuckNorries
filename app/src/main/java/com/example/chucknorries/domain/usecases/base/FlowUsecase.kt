package com.example.chucknorries.domain.usecases.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUsecase<in Params, out T> {

    abstract val dispatcher:CoroutineDispatcher

    abstract fun execute(params: Params?=null):Flow<T>

    operator fun invoke(params: Params? = null): Flow<T> = execute(params).flowOn(dispatcher)

}