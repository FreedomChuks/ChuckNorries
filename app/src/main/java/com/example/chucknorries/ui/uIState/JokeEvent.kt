package com.example.chucknorries.ui.uIState

sealed class JokeEvent{
    object FetchRandomJokes:JokeEvent()
    data class SearchJokes(val query:String):JokeEvent()
    object FetchCategory:JokeEvent()
    data class FetchByCategory(val category:String):JokeEvent()
    object FetchFavouriteJokes:JokeEvent()
}
