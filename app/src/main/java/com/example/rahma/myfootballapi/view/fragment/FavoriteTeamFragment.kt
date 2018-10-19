package com.example.rahma.myfootballapi.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.rahma.myfootballapi.*

import com.example.rahma.myfootballapi.adapter.FavoriteTeamsAdapter
import com.example.rahma.myfootballapi.api.database
import com.example.rahma.myfootballapi.api.databaseTeam
import com.example.rahma.myfootballapi.model.Favorite
import com.example.rahma.myfootballapi.model.FavoriteTeam
import com.example.rahma.myfootballapi.view.activity.DetailActivity
import com.example.rahma.myfootballapi.view.activity.TeamDetaiActivity
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh


class FavoriteTeamFragment : Fragment() {

    private var favorites: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamsAdapter
    private lateinit var recyclerTeams: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private fun showFavorite() {

        context?.databaseTeam?.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorites.clear()
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
            progressBar.invisible()
            swipeRefresh.isRefreshing = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite_team, container, false)
        progressBar = view.findViewById(R.id.progresBar)
        recyclerTeams = view.findViewById(R.id.recyclerFavoriteTeam) as RecyclerView
        swipeRefresh = view.findViewById(R.id.refreshFavoriteTeamsLayout)
        progressBar.visible()
        val layoutManager = GridLayoutManager(context, 3)
        recyclerTeams.layoutManager = layoutManager
        adapter = FavoriteTeamsAdapter(favorites) {
            ctx.startActivity<TeamDetaiActivity>(getIdTeam() to it.idTeam)
        }
        recyclerTeams.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            showFavorite()
        }
        return view
    }
}
