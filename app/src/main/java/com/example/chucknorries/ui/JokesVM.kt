package com.example.chucknorries.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chucknorries.data.ChuckNorrisRepositoryContract
import com.example.chucknorries.ui.uIState.JokeEvent
import com.example.chucknorries.ui.uIState.JokeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class JokesVM @Inject constructor(private val repositoryContract: ChuckNorrisRepositoryContract):ViewModel() {

    private val _uiState = MutableStateFlow(JokeUIState())
    val uiState:StateFlow<JokeUIState> get() = _uiState.asStateFlow()

     fun onTriggerEvent(event: JokeEvent){
        when(event){
            is JokeEvent.FetchByCategory -> {

            }
            JokeEvent.FetchCategory -> {

            }
            JokeEvent.FetchFavouriteJokes -> {

            }
            JokeEvent.FetchRandomJokes -> {
                fetchRandomJokes()
            }
            is JokeEvent.SearchJokes -> {

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

    private fun searchJokes(query:String){
       viewModelScope.launch {
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

}