package com.example.rahma.myfootballapi.view.activity

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.example.rahma.myfootballapi.*
import com.example.rahma.myfootballapi.adapter.ViewPagerAdapter
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.api.databaseTeam
import com.example.rahma.myfootballapi.model.FavoriteTeam
import com.example.rahma.myfootballapi.model.Team
import com.example.rahma.myfootballapi.presenter.TeamsPresenter
import com.example.rahma.myfootballapi.view.`interface`.TeamsView
import com.example.rahma.myfootballapi.view.fragment.FavoriteMatchFragment
import com.example.rahma.myfootballapi.view.fragment.MatchFragment
import com.example.rahma.myfootballapi.view.fragment.PlayerFragment
import com.example.rahma.myfootballapi.view.fragment.TeamDescription
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detai.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class TeamDetaiActivity : AppCompatActivity(), TeamsView {
    private lateinit var presenter: TeamsPresenter
    private lateinit var dataTeam: List<Team>
    private lateinit var progressBar: ProgressBar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detai)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        favoriteState()
        progressBar = findViewById(R.id.progresBar)
        val request = Api()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)
        presenter.getTeamDetail(intent.getStringExtra(getIdTeam()))
        viewPager = findViewById(R.id.viewpagerTeam) as ViewPager
        setupViewPager(viewPager)
        tabLayout = findViewById(R.id.tabsTeam) as TabLayout
        tabLayout.setupWithViewPager(viewPager)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(TeamDescription.newInstance(intent.getStringExtra(getIdTeam())), getOverview())
        adapter.addFragment(PlayerFragment.newInstance(intent.getStringExtra(getIdTeam())), getPlayer())
        viewPager.adapter = adapter
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        dataTeam = data
        Picasso.get().load(data[0].strTeamBadge).into(teamBadge)
        teamYear.text = data[0].intTeamFormedYear
        teamStadion.text = data[0].strTeamStadium
        teamName.text = data[0].strTeamName
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_fav -> {
                try {
                    if (isFavorite) removeFromFavorite() else addToFavorite()

                    isFavorite = !isFavorite
                    setFavorite()
                }
                catch (e : Exception){
                    toast("Try again")
                }
                    true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun removeFromFavorite() {
        try {
            databaseTeam.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM, "(ID_TEAM = {id})",
                        "id" to intent.getStringExtra(getIdTeam()))
            }
            toast("Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.not_favorite)
    }

    private fun favoriteState() {
        databaseTeam.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(ID_TEAM = {id})",
                            "id" to intent.getStringExtra(getIdTeam()))
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            databaseTeam.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        FavoriteTeam.ID_TEAM to intent.getStringExtra(getIdTeam()),
                        FavoriteTeam.STR_TEAM to dataTeam[0].strTeamName,
                        FavoriteTeam.STR_TEAM_BADGE to dataTeam[0].strTeamBadge)
            }
            toast("Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
        }
    }
}
