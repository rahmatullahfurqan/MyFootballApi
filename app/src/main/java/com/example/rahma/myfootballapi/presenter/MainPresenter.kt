package com.example.rahma.myfootballapi.presenter

import com.example.rahma.myfootballapi.CoroutineContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.model.MatchResponse
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.view.`interface`.MainView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MainPresenter(private val view: MainView,
                    private val apiRepository: Api,
                    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getMatchList(typeMatch: String?, idLeague: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getMatch(typeMatch, idLeague)),
                        MatchResponse::class.java
                )
            }
            view.showMatchList(data.await().events)
            view.hideLoading()
        }

    }

}