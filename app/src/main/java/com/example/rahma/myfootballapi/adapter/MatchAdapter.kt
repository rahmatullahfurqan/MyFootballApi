package com.example.rahma.myfootballapi.adapter

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.rahma.myfootballapi.model.Match
import com.example.rahma.myfootballapi.R
import com.example.rahma.myfootballapi.getIdTeam
import com.example.rahma.myfootballapi.getNewSdf
import com.example.rahma.myfootballapi.getOldSdf
import com.example.rahma.myfootballapi.view.fragment.TeamDescription
import java.text.SimpleDateFormat
import java.util.*

class MatchAdapter (private val match : List<Match>, private val listener: (Match) -> Unit): RecyclerView.Adapter<MatchAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_match, parent, false))
     }

    override fun getItemCount(): Int=match.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(match[position],listener)
    }


    class TeamViewHolder(view : View) :RecyclerView.ViewHolder(view) {
        val dateEvents = view.findViewById(R.id.dateEvents)as TextView
        val homeTeam = view.findViewById(R.id.homeTeam)as TextView
        val awayTeam = view.findViewById(R.id.awayTeam)as TextView
        val homeScore = view.findViewById(R.id.homeScore)as TextView
        val awayScore = view.findViewById(R.id.awayScore)as TextView
        val timeEvent = view.findViewById(R.id.timeEvents)as TextView
        fun bind(match: Match, listener: (Match) -> Unit) {
            val oldSdf = SimpleDateFormat(getOldSdf())
            val newSdf = SimpleDateFormat(getNewSdf(), Locale.getDefault())
            val newDate = newSdf.format(oldSdf.parse(match.dateEvent))
            val sdf = SimpleDateFormat("hh:mm:ss")
            val newSdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())
            val newTime = newSdfTime.format(sdf.parse(match.strTime))
            timeEvent.text=newTime
            dateEvents.text= newDate
            homeTeam.text=match.strHomeTeam
            awayTeam.text=match.strAwayTeam
            homeScore.text=match.intHomeScore
            awayScore.text=match.intAwayScore
            itemView.setOnClickListener {
                listener(match)
            }
        }
    }


}


