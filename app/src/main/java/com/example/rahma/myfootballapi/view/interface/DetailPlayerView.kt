package com.example.rahma.myfootballapi.view.`interface`

import com.example.rahma.myfootballapi.model.Player

interface DetailPlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayer(data: List<Player>)
}