package com.example.rahma.myfootballapi.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.rahma.myfootballapi.R
import com.example.rahma.myfootballapi.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.sdk25.coroutines.onClick

class TeamsAdapter(private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_teams, parent, false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size

}
class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val teamBadge: ImageView = view.findViewById(R.id.teamBadge)
    private val teamName: TextView = view.findViewById(R.id.teamName)

    fun bindItem(teams: Team, listener: (Team) -> Unit) {
        if (teams.strTeamBadge!=null) {
            Picasso.get().load(teams.strTeamBadge).into(teamBadge)
        }
        teamName.text = teams.strTeamName
        itemView.onClick { listener(teams) }
    }
}