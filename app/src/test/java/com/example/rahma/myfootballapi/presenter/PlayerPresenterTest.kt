package com.example.rahma.myfootballapi.presenter

import com.example.rahma.myfootballapi.TestContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.model.Player
import com.example.rahma.myfootballapi.model.PlayerResponse
import com.example.rahma.myfootballapi.view.`interface`.PlayerView
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {
    @Mock
    private
    lateinit var view: PlayerView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: Api
    private lateinit var presenter: PlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(view, apiRepository, gson, TestContextProvider())
    }
    @Test
    fun getPlayerList() {
        val player: MutableList<Player> = mutableListOf()
        val response = PlayerResponse(player)
        val idTeam = "133604"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPlayerList(idTeam)),
                PlayerResponse::class.java
        )).thenReturn(response)

        presenter.getPlayerList(idTeam)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPlayer(player)
        Mockito.verify(view).hideLoading()
    }
}