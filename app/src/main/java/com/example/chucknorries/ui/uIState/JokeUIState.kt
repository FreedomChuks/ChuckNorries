package com.example.chucknorries.ui.uIState

import com.example.chucknorries.domain.JokesEntity

data class JokeUIState(
    val isLoading:Boolean=false,
    val jokeData:List<JokesEntity> = emptyList(),
    val errorMessage:String?=null

)