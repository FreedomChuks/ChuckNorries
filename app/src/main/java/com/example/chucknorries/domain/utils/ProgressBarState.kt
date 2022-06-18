package com.example.chucknorries.domain.utils

sealed class ProgressBarState{
    object Loading:ProgressBarState()
    object Idle:ProgressBarState()
}
