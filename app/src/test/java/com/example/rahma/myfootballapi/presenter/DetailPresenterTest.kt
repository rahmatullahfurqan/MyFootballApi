package com.example.rahma.myfootballapi.presenter

import com.example.rahma.myfootballapi.TestContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.model.DetailMatch
import com.example.rahma.myfootballapi.model.DetailMatchResponse
import com.example.rahma.myfootballapi.model.Team
import com.example.rahma.myfootballapi.model.TeamResponse
import com.example.rahma.myfootballapi.view.`interface`.DetailView
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPresenterTest {

    @Mock
    private
    lateinit var view: DetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: Api
    private lateinit var presenter: DetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getHomeIcon() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val idTeam = "133636"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getHomeIcon(idTeam)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showHomeIcon(teams)
    }

    @Test
    fun getAwayIcon() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val idTeam = "133610"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getAwayIcon(idTeam)

        Mockito.verify(view).showAwayIcon(teams)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getEventsDetail() {
        val events: MutableList<DetailMatch> = mutableListOf()
        val response = DetailMatchResponse(events)
        val idEvent = "576526"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getDetailMatch(idEvent)),
                DetailMatchResponse::class.java
        )).thenReturn(response)

        presenter.getEventsDetail(idEvent)

        Mockito.verify(view).showDetailMatch(events)
    }

}