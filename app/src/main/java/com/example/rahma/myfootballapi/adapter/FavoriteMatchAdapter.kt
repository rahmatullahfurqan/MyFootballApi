package com.example.rahma.myfootballapi.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.rahma.myfootballapi.model.Favorite
import com.example.rahma.myfootballapi.R
import com.example.rahma.myfootballapi.getNewSdf
import com.example.rahma.myfootballapi.getOldSdf
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

class FavoriteMatchAdapter(private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_match, parent, false))

    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val homeTeam = view.findViewById(R.id.homeTeam) as TextView
    val awayTeam = view.findViewById(R.id.awayTeam) as TextView
    val homeScore = view.findViewById(R.id.homeScore) as TextView
    val awayScore = view.findViewById(R.id.awayScore) as TextView
    val dateEvents = view.findViewById(R.id.dateEvents) as TextView
    val dateTime = view.findViewById(R.id.timeEvents) as TextView
    fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {
        val oldSdf = SimpleDateFormat(getOldSdf())
        val newSdf = SimpleDateFormat(getNewSdf(), Locale.getDefault())
        val newDate = newSdf.format(oldSdf.parse(favorite.dateEvent))
        val sdf = SimpleDateFormat("hh:mm:ss")
        val newSdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())
        val newTime = newSdfTime.format(sdf.parse(favorite.strTime))
        dateTime.text = newTime
        dateEvents.text = newDate
        homeTeam.text = favorite.strHomeTeam
        awayTeam.text = favorite.strAwayTeam
        homeScore.text = favorite.strHomeScore
        awayScore.text = favorite.strAwayScore
        itemView.onClick { listener(favorite) }
    }
}