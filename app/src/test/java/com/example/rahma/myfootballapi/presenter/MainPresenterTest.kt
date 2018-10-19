package com.example.rahma.myfootballapi.presenter

import com.example.rahma.myfootballapi.TestContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.model.Match
import com.example.rahma.myfootballapi.model.MatchResponse
import com.example.rahma.myfootballapi.view.`interface`.MainView
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainPresenterTest {
    @Mock
    private
    lateinit var view: MainView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: Api
    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson, TestContextProvider())
    }
    @Test
    fun getMatchList() {
        val events: MutableList<Match> = mutableListOf()
        val response = MatchResponse(events)
        val typeMatch = "eventspastleague.php"
        val idLeague = "4238"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getMatch(typeMatch,idLeague)),
                MatchResponse::class.java
        )).thenReturn(response)

        presenter.getMatchList(typeMatch,idLeague)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(events)
        Mockito.verify(view).hideLoading()
    }
}