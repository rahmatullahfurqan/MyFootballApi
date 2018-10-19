package com.example.rahma.myfootballapi.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.rahma.myfootballapi.*
import com.example.rahma.myfootballapi.adapter.FavoriteMatchAdapter
import com.example.rahma.myfootballapi.adapter.FavoriteTeamsAdapter
import com.example.rahma.myfootballapi.api.database
import com.example.rahma.myfootballapi.model.Favorite
import com.example.rahma.myfootballapi.view.activity.DetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteMatchFragment : Fragment() {
    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteMatchAdapter
    private lateinit var recyclerMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private fun showFavorite() {

        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.clear()
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
            progressBar.invisible()
            swipeRefresh.isRefreshing = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match_favorite, container, false)
        progressBar = view.findViewById(R.id.progresBar)
        recyclerMatch = view.findViewById(R.id.recyclerMatchFavorite) as RecyclerView
        swipeRefresh = view.findViewById(R.id.refreshFavoriteMatchLayout)
        progressBar.visible()
        recyclerMatch.layoutManager = LinearLayoutManager(ctx)
        adapter = FavoriteMatchAdapter(favorites) {
            ctx.startActivity<DetailActivity>(getIdEvent() to it.idEvent)
        }
        recyclerMatch.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            showFavorite()
        }
        return view
    }

}