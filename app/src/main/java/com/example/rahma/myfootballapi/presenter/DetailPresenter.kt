package com.example.rahma.myfootballapi.presenter

import android.support.annotation.UiThread
import com.example.rahma.myfootballapi.CoroutineContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.view.`interface`.DetailView
import com.example.rahma.myfootballapi.model.TeamResponse
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.model.DetailMatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: Api,
                      private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getHomeIcon(idTeam: String?) {
        view.showLoading()
        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                        TeamResponse::class.java
                )
            }
            view.showHomeIcon(data.await().teams)
        }

    }

    fun getAwayIcon(idTeam: String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                        TeamResponse::class.java
                )
            }
            view.showAwayIcon(data.await().teams)
            view.hideLoading()
        }
    }

    fun getEventsDetail(idEvent: String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getDetailMatch(idEvent)),
                        DetailMatchResponse::class.java
                )
            }
            view.showDetailMatch(data.await().events)

        }

    }
}