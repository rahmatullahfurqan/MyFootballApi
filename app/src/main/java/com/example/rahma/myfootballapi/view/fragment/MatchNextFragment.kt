package com.example.rahma.myfootballapi.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.example.rahma.myfootballapi.*
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.model.Match
import com.example.rahma.myfootballapi.adapter.MatchAdapter
import com.example.rahma.myfootballapi.adapter.MatchNextAdapter
import com.example.rahma.myfootballapi.presenter.MainPresenter
import com.example.rahma.myfootballapi.view.`interface`.MainView
import com.example.rahma.myfootballapi.view.activity.DetailActivity
import com.google.gson.Gson
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity


class MatchNextFragment : Fragment(), MainView {
    private lateinit var recyclerMatch: RecyclerView
    private var match: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MatchNextAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var spinnerLeague : Spinner
    private lateinit var leagueName: String
    private  var idLeague: String ="4328"
    override fun showMatchList(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match_next, container, false)
        progressBar = view.findViewById(R.id.progresBar)
        recyclerMatch = view.findViewById(R.id.recyclerNextMatch) as RecyclerView
        swipeRefresh = view.findViewById(R.id.refreshNextLayout)
        adapter = MatchNextAdapter(match) {
            startActivity<DetailActivity>(getIdEvent() to it.idEvent)
        }
        recyclerMatch.layoutManager = LinearLayoutManager(ctx)
        recyclerMatch.adapter = adapter
        val request = Api()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)
        spinnerLeague = view?.findViewById(R.id.spinerLeague) as Spinner
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerLeague.adapter = spinnerAdapter
        spinnerLeague.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinnerLeague.selectedItem.toString()
                idLeague = getIdLeague(leagueName)
                presenter.getMatchList(getNextLeague(), idLeague)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        swipeRefresh.onRefresh {
            presenter.getMatchList(getNextLeague(),idLeague)
        }
        return view
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

}
