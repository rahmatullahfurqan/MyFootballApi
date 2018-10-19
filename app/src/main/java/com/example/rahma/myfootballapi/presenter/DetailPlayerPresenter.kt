package com.example.rahma.myfootballapi.presenter

import com.example.rahma.myfootballapi.CoroutineContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.model.DetailPlayerResponse
import kotlinx.coroutines.experimental.async
import com.example.rahma.myfootballapi.view.`interface`.DetailPlayerView
import com.google.gson.Gson
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPlayerPresenter(private val view: DetailPlayerView,
                            private val apiRepository: Api,
                            private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getPlayerDetail(idPlayer: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayerDetailList(idPlayer)),
                        DetailPlayerResponse::class.java
                )
            }
            view.showPlayer(data.await().players)
            view.hideLoading()
        }

    }

}