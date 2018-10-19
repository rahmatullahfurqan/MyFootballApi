package com.example.rahma.myfootballapi.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.rahma.myfootballapi.R
import com.example.rahma.myfootballapi.model.FavoriteTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteTeamsAdapter(private val favorite: List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamsViewHolder {
        return FavoriteTeamsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_teams, parent, false))

    }

    override fun onBindViewHolder(holder: FavoriteTeamsViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}

class FavoriteTeamsViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val strTeamBadge = view?.findViewById(R.id.teamBadge) as ImageView
    val strTeamName = view?.findViewById(R.id.teamName) as TextView
    fun bindItem(favorite: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        strTeamName.text=favorite.strTeam
        Picasso.get().load(favorite.strTeamBadge).into(strTeamBadge)
        itemView.onClick { listener(favorite) }
    }
}