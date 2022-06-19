package com.example.chucknorries.ui.viewState

import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.domain.utils.ProgressBarState
import com.example.chucknorries.domain.utils.UIComponent

data class JokeUIState(
    val isLoading:ProgressBarState = ProgressBarState.Idle,
    val jokeData:List<JokesEntity> = emptyList(),
    val categories:List<String> = emptyList(),
    val errorMessage:UIComponent?=null,
    val isSaved:Boolean = false,
)