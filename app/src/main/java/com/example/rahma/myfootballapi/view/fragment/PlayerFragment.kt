package com.example.rahma.myfootballapi.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.rahma.myfootballapi.*

import com.example.rahma.myfootballapi.adapter.PlayerAdapter
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.model.Player
import com.example.rahma.myfootballapi.presenter.PlayerPresenter
import com.example.rahma.myfootballapi.view.`interface`.PlayerView
import com.example.rahma.myfootballapi.view.activity.DetailPlayerActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.fragment_teams.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class PlayerFragment : Fragment(), PlayerView {
    private lateinit var recyclerPlayer: RecyclerView
    private lateinit var adapter: PlayerAdapter
    private lateinit var presenter: PlayerPresenter
    private lateinit var progresBar: ProgressBar
    private var player: MutableList<Player> = mutableListOf()
    override fun showLoading() {
        progresBar.visible()
    }

    override fun hideLoading() {
        progresBar.invisible()
    }

    override fun showPlayer(data: List<Player>) {
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerPlayer = view?.findViewById(R.id.recyclerPlayer) as RecyclerView
        progresBar = view?.findViewById(R.id.progresBar) as ProgressBar
        adapter = PlayerAdapter(player) {
            startActivity<DetailPlayerActivity>(getIdPlayer() to it.idPlayer)

        }
        recyclerPlayer.layoutManager = LinearLayoutManager(ctx)
        recyclerPlayer.adapter = adapter
        val request = Api()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)
        presenter.getPlayerList(arguments!!.getString(getIdTeam()))

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    companion object {
        fun newInstance(idTeam: String?): PlayerFragment {
            val args = Bundle()
            args.putString(getIdTeam(), idTeam)
            val fragment = PlayerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
