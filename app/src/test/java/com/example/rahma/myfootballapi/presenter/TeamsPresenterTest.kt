package com.example.rahma.myfootballapi.presenter

import com.example.rahma.myfootballapi.TestContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.model.Team
import com.example.rahma.myfootballapi.model.TeamResponse
import com.example.rahma.myfootballapi.view.`interface`.TeamsView
import com.google.gson.Gson
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamsPresenterTest {
    @Mock
    private
    lateinit var view: TeamsView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: Api
    private lateinit var presenter: TeamsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamsPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val idLeague = "4328"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeam(idLeague)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getTeamList(idLeague)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(teams)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getTeamDetail() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val idTeam = "133604"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getTeamDetail(idTeam)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(teams)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getTeamByName() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val query = "arsenal"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeambyName(query)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getTeamDetail(query)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(teams)
        Mockito.verify(view).hideLoading()
    }
}