package com.example.rahma.myfootballapi.presenter

import com.example.rahma.myfootballapi.CoroutineContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.model.TeamResponse
import com.example.rahma.myfootballapi.view.`interface`.TeamsView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: Api,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(idLeague: String?) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeam(idLeague)),
                        TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }

    fun getTeamDetail(idTeam: String?) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                        TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }

    fun getTeamByName(query: String?) {

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeambyName(query)),
                        TeamResponse::class.java
                )
            }
            view.showLoading()
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}