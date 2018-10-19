package com.example.rahma.myfootballapi.presenter

import com.example.rahma.myfootballapi.TestContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.model.DetailPlayerResponse
import com.example.rahma.myfootballapi.model.Player
import com.example.rahma.myfootballapi.view.`interface`.DetailPlayerView
import com.google.gson.Gson
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPlayerPresenterTest {

    @Mock
    private
    lateinit var view: DetailPlayerView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: Api
    private lateinit var presenter: DetailPlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPlayerPresenter(view, apiRepository, gson, TestContextProvider())
    }
    @Test
    fun getPlayerDetail() {
        val players: MutableList<Player> = mutableListOf()
        val response = DetailPlayerResponse(players)
        val idPlayer = "34145937"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPlayerDetailList(idPlayer)),
                DetailPlayerResponse::class.java
        )).thenReturn(response)

        presenter.getPlayerDetail(idPlayer)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPlayer(players)
        Mockito.verify(view).hideLoading()
    }
}