package com.example.chucknorries.ui.viewState

import com.example.chucknorries.domain.entities.JokesEntity

sealed class JokeEvent{
    object FetchRandomJokes:JokeEvent()
    data class SearchJokes(val query:String):JokeEvent()
    object FetchCategory:JokeEvent()
    data class FetchByCategory(val category:String):JokeEvent()
    object FetchFavouriteJokes:JokeEvent()
    data class SaveFavouriteJokes(val entity: JokesEntity):JokeEvent()
}
