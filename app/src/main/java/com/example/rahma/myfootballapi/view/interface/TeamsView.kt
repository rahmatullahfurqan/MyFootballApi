package com.example.rahma.myfootballapi.view.`interface`

import com.example.rahma.myfootballapi.model.Team

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}