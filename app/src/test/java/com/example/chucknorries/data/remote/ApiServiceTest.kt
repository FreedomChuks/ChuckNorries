package com.example.chucknorries.data.remote

import com.example.chucknorries.utils.JokeRequestDispatcher
import com.example.chucknorries.utils.REQUEST_PATH
import com.example.chucknorries.utils.makeTestApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: ApiService


    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.dispatcher = JokeRequestDispatcher()
        api = makeTestApiService(mockWebServer)
    }


    /**
     * Integration test -
     * ensures the https://api.chucknorris.io/jokes/random) returns results from the api
     */
    @Test
    fun `check that calling getRandomJokes makes a GET request`() = runBlocking {
        api.getRandomJokes()
        assertThat("GET $REQUEST_PATH HTTP/1.1").isEqualTo(mockWebServer.takeRequest().requestLine)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check that calling getRandomJokes returns correct data`() = runTest {
        val response = api.getRandomJokes()
        Assert.assertTrue(response.id=="085c_ObbSFSpHwHEdurqGQ")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check that calling getRandomJokes returns astronomyList`() = runTest {
        val pictures = api.getRandomJokes()
        assertThat(pictures).isNotNull()
    }

    @Test
    fun `check that calling getRandomJokes makes request to given path`() = runBlocking {
        api.getRandomJokes()
        assertThat(REQUEST_PATH).isEqualTo(mockWebServer.takeRequest().path)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}