package com.example.rahma.myfootballapi.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.example.rahma.myfootballapi.*

import com.example.rahma.myfootballapi.R.array.league
import com.example.rahma.myfootballapi.adapter.TeamsAdapter
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.model.Team
import com.example.rahma.myfootballapi.presenter.TeamsPresenter
import com.example.rahma.myfootballapi.view.`interface`.TeamsView
import com.example.rahma.myfootballapi.view.activity.TeamDetaiActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_teams.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class TeamsFragment : Fragment(), TeamsView {
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamsAdapter
    private lateinit var recyclerTeams: RecyclerView
    private lateinit var spinnerLeague: Spinner
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String
    private lateinit var idLeague: String
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(view?.findViewById(R.id.toolbar))
            (activity as AppCompatActivity).supportActionBar?.title = getTeam()
        }
        recyclerTeams = view?.findViewById(R.id.recyclerTeams) as RecyclerView
        spinnerLeague = view?.findViewById(R.id.spinerLeague) as Spinner
        progressBar = view?.findViewById(R.id.progresBar) as ProgressBar
        swipeRefresh = view?.findViewById(R.id.refreshTeam) as SwipeRefreshLayout
        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerLeague.adapter = spinnerAdapter

        adapter = TeamsAdapter(teams) {
            startActivity<TeamDetaiActivity>(getIdTeam() to it.intTeamId)
        }
        val layoutManager = GridLayoutManager(context, 3)
        recyclerTeams.layoutManager = layoutManager as RecyclerView.LayoutManager?
        recyclerTeams.adapter = adapter
        val request = Api()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)
        spinnerLeague.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinnerLeague.selectedItem.toString()
                idLeague= getIdLeague(leagueName)
                presenter.getTeamList(idLeague)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getTeamList(idLeague)
        }
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != "") {
                    presenter.getTeamByName(newText)
                } else {
                    spinnerLeague.setSelection(0)
                    leagueName = spinnerLeague.selectedItem.toString()
                    presenter.getTeamList(leagueName)
                }
                return false
            }

        })

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun onResume() {
        super.onResume()
        searchView.isFocusable=false
    }
    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }


}
