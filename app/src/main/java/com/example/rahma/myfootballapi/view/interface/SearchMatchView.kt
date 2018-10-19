package com.example.rahma.myfootballapi.view.`interface`

import com.example.rahma.myfootballapi.model.Match

interface SearchMatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatch(data: List<Match>)
}