package com.example.rahma.myfootballapi.presenter

import com.example.rahma.myfootballapi.TestContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.model.Match
import com.example.rahma.myfootballapi.model.SearchResponse
import com.example.rahma.myfootballapi.view.`interface`.SearchMatchView
import com.google.gson.Gson
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchPresenterTest {
    @Mock
    private
    lateinit var view: SearchMatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: Api
    private lateinit var presenter: SearchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getSearhMatch() {
        val event: MutableList<Match> = mutableListOf()
        val response = SearchResponse(event)
        val query = "arsenal"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getMatchbyName(query)),
                SearchResponse::class.java
        )).thenReturn(response)

        presenter.getSearhMatch(query)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatch(event)
        Mockito.verify(view).hideLoading()
    }
}