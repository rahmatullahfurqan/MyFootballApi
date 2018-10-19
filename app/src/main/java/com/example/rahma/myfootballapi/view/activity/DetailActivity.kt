package com.example.rahma.myfootballapi.view.activity

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.rahma.myfootballapi.*
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.model.DetailMatch
import com.example.rahma.myfootballapi.model.Favorite
import com.example.rahma.myfootballapi.model.Team
import com.example.rahma.myfootballapi.presenter.DetailPresenter
import com.example.rahma.myfootballapi.R.drawable.favorite
import com.example.rahma.myfootballapi.R.drawable.not_favorite
import com.example.rahma.myfootballapi.R.id.add_fav
import com.example.rahma.myfootballapi.R.menu.detail_menu
import com.example.rahma.myfootballapi.api.database
import com.example.rahma.myfootballapi.view.`interface`.DetailView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception


class DetailActivity : AppCompatActivity(), DetailView {
    private lateinit var presenter: DetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var data: List<DetailMatch>
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        favoriteState()
        val request = Api()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)
        val id = intent.getStringExtra(getIdEvent())
        presenter.getEventsDetail(id)


    }

    override fun initData(matchModel: DetailMatch) {
        val oldSdf = SimpleDateFormat(getOldSdf())
        val newSdf = SimpleDateFormat(getNewSdf(), Locale.getDefault())
        val newDate = newSdf.format(oldSdf.parse(matchModel.dateEvent))
        val sdf = SimpleDateFormat("hh:mm:ss")
        val newSdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())
        val newTime = newSdfTime.format(sdf.parse(matchModel.strTime))
        timeEvents.text = newTime
        supportActionBar?.title = matchModel.strHomeTeam + getVs() + matchModel.strAwayTeam
        dateEvents.text = newDate
        homeTeam.text = matchModel.strHomeTeam
        awayTeam.text = matchModel.strAwayTeam
        homeScore.text = matchModel.intHomeScore
        awayScore.text = matchModel.intAwayScore
        homeGoals.text = matchModel.strHomeGoalDetails
        awayGoals.text = matchModel.strAwayGoalDetails
        homeFormation.text = matchModel.strHomeFormation
        awayFormation.text = matchModel.strAwayFormation
        homeShots.text = matchModel.intHomeShots
        awayShots.text = matchModel.intAwayShots
        homeRedCard.text = matchModel.strHomeRedCards
        awayRedCard.text = matchModel.strAwayRedCards
        homeYellowCard.text = matchModel.strHomeYellowCards
        awayYellowCard.text = matchModel.strAwayYellowCards
        homeGoalKeeper.text = matchModel.strHomeLineupGoalkeeper
        awayGoalKeeper.text = matchModel.strAwayLineupGoalkeeper
        homeDefense.text = matchModel.strHomeLineupDefense
        awayDefense.text = matchModel.strAwayLineupDefense
        homeMidfield.text = matchModel.strHomeLineupMidfield
        awayMidfield.text = matchModel.strAwayLineupMidfield
        homeForward.text = matchModel.strHomeLineupForward
        awayForward.text = matchModel.strAwayLineupForward
        homeSubtituties.text = matchModel.strHomeLineupSubstitutes
        awaySubtituties.text = matchModel.strAwayLineupSubstitutes
        presenter.getHomeIcon(matchModel.idHomeTeam.toString())
        presenter.getAwayIcon(matchModel.idAwayTeam.toString())
    }

    override fun showHomeIcon(dataTeam: List<Team>) {
        Picasso.get().load(dataTeam[0].strTeamBadge).into(homeIcon)
    }

    override fun showAwayIcon(dataTeam: List<Team>) {
        Picasso.get().load(dataTeam[0].strTeamBadge).into(awayIcon)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_fav -> {
                try {
                    if (isFavorite) removeFromFavorite() else addToFavorite()
                    isFavorite = !isFavorite
                    setFavorite()
                }
                catch (e : Exception){
                    toast("try again")
                }
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun showDetailMatch(dataMatch: List<DetailMatch>) {
        data = dataMatch
        initData(data[0])
    }

    override fun showLoading() {
        homeProgresBar.visible()
        awayProgresBar.visible()
    }

    override fun hideLoading() {
        homeProgresBar.invisible()
        awayProgresBar.invisible()
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.ID_EVENT to intent.getStringExtra(getIdEvent()),
                        Favorite.strHomeTeam to data[0].strHomeTeam,
                        Favorite.strAwayTeam to data[0].strAwayTeam,
                        Favorite.str_Home_Score to data[0].intHomeScore,
                        Favorite.str_Away_Score to data[0].intAwayScore,
                        Favorite.dateEvent to data[0].dateEvent,
                        Favorite.strTime to data[0].strTime)

            }
            toast("Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(ID_EVENT = {id})",
                        "id" to intent.getStringExtra(getIdEvent()))
            }
            toast("Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, not_favorite)
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(ID_EVENT = {id})",
                            "id" to intent.getStringExtra(getIdEvent()))
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

}
