package com.example.chucknorries.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chucknorries.data.JokesRepositoryContract
import com.example.chucknorries.domain.entities.JokesEntity
import com.example.chucknorries.domain.utils.DataState
import com.example.chucknorries.domain.utils.ProgressBarState
import com.example.chucknorries.domain.utils.UIComponent
import com.example.chucknorries.ui.viewState.JokeEvent
import com.example.chucknorries.ui.viewState.JokeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class JokesVM @Inject constructor(private val repositoryContract: JokesRepositoryContract):ViewModel() {
    private val _uiState = MutableStateFlow(JokeUIState())
    val uiState:StateFlow<JokeUIState> get() = _uiState.asStateFlow()

    fun onTriggerEvent(event: JokeEvent){
        when(event){
            is JokeEvent.FetchByCategory -> {
                fetchCategoryByCategory(event.category)
            }
            JokeEvent.FetchCategory -> {
                fetchCategory()
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
                when(dataState){
                    is DataState.Data -> {
                        _uiState.update { it.copy(jokeData = listOf(dataState.data)) }
                    }
                    is DataState.Error -> {
                        _uiState.update { it.copy(errorMessage = dataState.uiComponent) }
                    }
                    is DataState.Loading -> {
                        _uiState.update { it.copy(isLoading = dataState.progressBarState) }
                    }
                }
            }
        }
    }

    fun jokeShown(){
        _uiState.update { it.copy(jokeData = emptyList(), isLoading = ProgressBarState.Idle) }
    }

    fun errorShown(){
        _uiState.update { it.copy(errorMessage = null, isLoading =ProgressBarState.Idle ) }
    }

    private fun searchJokes(query:String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                _uiState.update {
                    it.copy(
                        errorMessage = UIComponent.Dialog(
                            title = "validation",
                            description = "search field cannot be empty"
                        )
                    )
                }
                return@launch
            }
            repositoryContract.searchJokes(query).collect { dataState ->
                when (dataState) {
                    is DataState.Data -> {
                        _uiState.update { it.copy(jokeData = dataState.data.result) }
                    }
                    is DataState.Error -> {
                        _uiState.update { it.copy(errorMessage = dataState.uiComponent) }
                    }
                    is DataState.Loading -> {
                        _uiState.update { it.copy(isLoading = dataState.progressBarState) }
                    }
                }

            }
        }
    }

    private fun favouriteJokes(entity: JokesEntity){
        viewModelScope.launch(IO) {
            val cache = repositoryContract.isJokeExits(entity.id)
            if (!cache){
                repositoryContract.saveFavouriteJokes(entity)
                _uiState.update { it.copy(isSaved = true) }
            }else {
                repositoryContract.deleteFavouriteJoke(entity)
                _uiState.update { it.copy(isSaved = false) }
            }
        }
    }

    private fun fetchCacheJoke(){
        viewModelScope.launch {
            repositoryContract.fetchJokesFromCache().collect{ dataState->
                when(dataState){
                    is DataState.Data -> {
                        _uiState.update { it.copy(jokeData = dataState.data) }
                    }
                    is DataState.Error -> {
                        _uiState.update { it.copy(errorMessage = dataState.uiComponent) }
                    }
                    is DataState.Loading -> {
                        _uiState.update{it.copy(isLoading = dataState.progressBarState)}
                    }
                }
            }
        }
    }

    private fun fetchCategory(){
        viewModelScope.launch {
            repositoryContract.fetchJokeCategories().collect{ dataState->
                when(dataState){
                    is DataState.Data -> {
                        Timber.i("data ${dataState.data}")
                        _uiState.update { it.copy(categories = dataState.data) }
                    }
                    is DataState.Error -> {
                        _uiState.update { it.copy(errorMessage = dataState.uiComponent) }
                    }
                    is DataState.Loading -> {
                        _uiState.update { it.copy(isLoading = dataState.progressBarState) }
                    }
                }
            }
        }
    }

    private fun fetchCategoryByCategory(category:String){
        viewModelScope.launch {
            repositoryContract.fetchJokeByCategory(category).collect{ dataState->
                when(dataState){
                    is DataState.Data -> {
                        _uiState.update { it.copy(jokeData = listOf(dataState.data)) }
                    }
                    is DataState.Error -> {
                        _uiState.update { it.copy(errorMessage = dataState.uiComponent) }
                    }
                    is DataState.Loading -> {
                        _uiState.update { it.copy(isLoading = dataState.progressBarState) }
                    }
                }
            }
        }
    }
}