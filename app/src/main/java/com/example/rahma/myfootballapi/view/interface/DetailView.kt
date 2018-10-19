package com.example.rahma.myfootballapi.view.`interface`

import com.example.rahma.myfootballapi.model.DetailMatch
import com.example.rahma.myfootballapi.model.Team

interface DetailView {

    fun showHomeIcon(data: List<Team>)
    fun showAwayIcon(data: List<Team>)
    fun showDetailMatch(data: List<DetailMatch>)
    fun initData(MatchModel : DetailMatch)
    fun showLoading()
    fun hideLoading()
}