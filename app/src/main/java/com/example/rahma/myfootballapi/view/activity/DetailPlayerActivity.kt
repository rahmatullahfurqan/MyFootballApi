package com.example.rahma.myfootballapi.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.rahma.myfootballapi.R
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.getIdPlayer
import com.example.rahma.myfootballapi.invisible
import com.example.rahma.myfootballapi.model.Player
import com.example.rahma.myfootballapi.presenter.DetailPlayerPresenter
import com.example.rahma.myfootballapi.view.`interface`.DetailPlayerView
import com.example.rahma.myfootballapi.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity(), DetailPlayerView {
    private lateinit var presenter: DetailPlayerPresenter
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showPlayer(data: List<Player>) {
        supportActionBar?.title = data[0].strPlayer
        Picasso.get().load(data[0].strFanart1).into(playerTumb)
        progresBar.invisible()
        playerHeight.text = data[0].strHeight!!.substring(0, 4)
        playerWeight.text = data[0].strWeight!!.substring(0, 4)
        playerPosisition.text = data[0].strPosition
        strDescription.text = data[0].strDescriptionEN
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        setSupportActionBar(findViewById(R.id.toolbar))
        progresBar.visible()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val request = Api()
        val gson = Gson()
        presenter = DetailPlayerPresenter(this, request, gson)
        presenter.getPlayerDetail(intent.getStringExtra(getIdPlayer()))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }
}
