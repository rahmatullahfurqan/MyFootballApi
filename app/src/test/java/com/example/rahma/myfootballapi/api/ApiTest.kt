package com.example.rahma.myfootballapi.api

import org.junit.Test

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiTest {

    @Test
    fun doRequest() {
        val apiRepository = mock(Api::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}