package com.example.rahma.myfootballapi.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.support.v7.widget.SearchView
import android.view.MenuItem
import android.widget.ProgressBar
import com.example.rahma.myfootballapi.R
import com.example.rahma.myfootballapi.adapter.MatchAdapter
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.getIdEvent
import com.example.rahma.myfootballapi.invisible
import com.example.rahma.myfootballapi.model.Match
import com.example.rahma.myfootballapi.presenter.SearchPresenter
import com.example.rahma.myfootballapi.view.`interface`.SearchMatchView
import com.example.rahma.myfootballapi.visible
import com.google.gson.Gson
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity

class SearchMatchMatch : AppCompatActivity(), SearchMatchView {
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    private var match: MutableList<Match> = mutableListOf()
    private lateinit var adapter: MatchAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: SearchPresenter
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.recyclerSearchMatch)
        progressBar = findViewById(R.id.progresBar)
        progressBar.invisible()
        adapter = MatchAdapter(match) {
            startActivity<DetailActivity>(getIdEvent() to it.idEvent)
        }
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        val request = Api()
        val gson = Gson()
        presenter = SearchPresenter(this, request, gson)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.getSearhMatch(newText)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {

            R.id.search -> {
                startActivity(Intent(ctx, SearchMatchMatch::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showMatch(data: List<Match>) {
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
