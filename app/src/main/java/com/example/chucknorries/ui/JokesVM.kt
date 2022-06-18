package com.example.chucknorries.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chucknorries.data.JokesRepositoryContract
import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.ui.uIState.JokeEvent
import com.example.chucknorries.ui.uIState.JokeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokesVM @Inject constructor(private val repositoryContract: JokesRepositoryContract):ViewModel() {

    private val _uiState = MutableStateFlow(JokeUIState())
    val uiState:StateFlow<JokeUIState> get() = _uiState.asStateFlow()

     fun onTriggerEvent(event: JokeEvent){
        when(event){
            is JokeEvent.FetchByCategory -> {

            }
            JokeEvent.FetchCategory -> {

            }
            JokeEvent.FetchFavouriteJokes -> {
                fetchCacheJoke()
            }
            JokeEvent.FetchRandomJokes -> {
                fetchRandomJokes()
            }
            is JokeEvent.SearchJokes -> {
                searchJokes(event.query)
            }
            is JokeEvent.SaveFavouriteJokes -> {
                favouriteJokes(event.entity)
            }
        }
    }

    private fun fetchRandomJokes(){
        viewModelScope.launch {
            repositoryContract.fetchRandomJokes().collect{ dataState->
                _uiState.update { it.copy(isLoading = dataState.isLoading) }

                dataState.data?.let { data ->
                    _uiState.update { it.copy(jokeData = listOf(data)) }
                }

                dataState.errorResponse?.let { e->
                    _uiState.update { it.copy(errorMessage = e.error) }
                }

                dataState.systemError?.let {e->
                    _uiState.update { it.copy(errorMessage = e) }
                }

            }
        }
    }

    fun jokeShown(){
        _uiState.update { it.copy(jokeData = emptyList()) }
    }

    fun errorShown(){
        _uiState.update { it.copy(errorMessage = null) }
    }

    private fun searchJokes(query:String){
        viewModelScope.launch {

            if (query.isEmpty()) {
                _uiState.update { it.copy(errorMessage = "Query cannot be empty") }
                return@launch
            }
            repositoryContract.searchJokes(query).collect{ dataState->
                _uiState.update { it.copy(isLoading = dataState.isLoading ) }

                dataState.data?.let {  data->
                    _uiState.update { it.copy(jokeData = data.result ) }
                }

                dataState.errorResponse?.let { e->
                    _uiState.update { it.copy(errorMessage = e.error ) }
                }
            }
        }
    }

    private fun favouriteJokes(entity: JokesEntity){
        viewModelScope.launch(IO) {
            val cache = repositoryContract.isJokeExits(entity.id)
                if (!cache){
                    repositoryContract.favouriteJokes(entity)
                    _uiState.update { it.copy(isSaved = true) }
                }else {
                    repositoryContract.deleteJoke(entity)
                    _uiState.update { it.copy(isSaved = false) }
                }
        }
    }

    private fun fetchCacheJoke(){
        viewModelScope.launch {
             repositoryContract.fetchJokesFromCache().collect{ dataState->
                 dataState.data?.let { data->
                     _uiState.update { it.copy(jokeData = data) }
                 }

             }
        }
    }

    fun checked(){
        _uiState.update { it.copy(isSaved = false) }
    }

}