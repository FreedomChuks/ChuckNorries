package com.example.chucknorries.data.repository


import com.example.chucknorries.data.JokesRepository
import com.example.chucknorries.data.remote.ApiService
import com.example.chucknorries.data.repository.fake.JokeFakeDAO
import com.example.chucknorries.data.repository.fake.JokeFakeDatabase
import com.example.chucknorries.domain.utils.DataState
import com.example.chucknorries.domain.utils.ProgressBarState
import com.example.chucknorries.utils.JokeRequestDispatcher
import com.example.chucknorries.utils.makeTestApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.jupiter.api.Test


class JokeFakeRepository {

    private val appDatabase = JokeFakeDatabase()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: ApiService

    //system in test
    private lateinit var jokeRepository: JokesRepository

    //dependencies
    private lateinit var cacheDataSource:JokeFakeDAO

    @Before
    fun setup(){
        //setup
        mockWebServer = MockWebServer()
        mockWebServer.start()
        cacheDataSource = JokeFakeDAO(appDatabase)
        mockWebServer.dispatcher = JokeRequestDispatcher()
        api = makeTestApiService(mockWebServer)

        //initiate system in test
        jokeRepository = JokesRepository(api,cacheDataSource)
    }



    @Test
    fun fetch_random_jokes():Unit = runBlocking {
        val networkEmissions = api.getRandomJokes()

        // Execute the use-case
        val emissions = jokeRepository.fetchRandomJokes().toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<Nothing>(ProgressBarState.Loading))


        //confirm data is fetched
        assertThat(networkEmissions.id).isNotNull()

        // Confirm second emission is data
        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data.id == "085c_ObbSFSpHwHEdurqGQ")

    }


}