package com.example.rahma.myfootballapi.view.`interface`

import com.example.rahma.myfootballapi.model.Match

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
}