package com.example.rahma.myfootballapi.presenter

import com.example.rahma.myfootballapi.CoroutineContextProvider
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.api.TheSportDBApi
import com.example.rahma.myfootballapi.model.SearchResponse
import com.example.rahma.myfootballapi.view.`interface`.SearchMatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class SearchPresenter(private val searchView: SearchMatchView,
                      private val apiRepository: Api,
                      private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getSearhMatch(query: String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getMatchbyName(query)),
                        SearchResponse::class.java
                )
            }
            searchView.showLoading()
            searchView.showMatch(data.await().event)
            searchView.hideLoading()
        }

    }
}