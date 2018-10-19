package com.example.rahma.myfootballapi.presenter

import com.example.rahma.myfootballapi.CoroutineContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.model.PlayerResponse
import com.example.rahma.myfootballapi.view.`interface`.PlayerView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerView,
                      private val apiRepository: Api,
                      private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getPlayerList(idTeam: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayerList(idTeam)),
                        PlayerResponse::class.java
                )
            }
            view.showPlayer(data.await().player)
            view.hideLoading()
        }

    }

}