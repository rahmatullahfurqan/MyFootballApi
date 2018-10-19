package com.example.rahma.myfootballapi.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.rahma.myfootballapi.R
import com.example.rahma.myfootballapi.model.Player
import com.squareup.picasso.Picasso

class PlayerAdapter (private val player : List<Player>, private val listener: (Player) -> Unit): RecyclerView.Adapter<PlayerAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_player, parent, false))
    }

    override fun getItemCount(): Int=player.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(player[position],listener)
    }


    class TeamViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val playerBadge = view.findViewById(R.id.playerBadge) as ImageView
        val playerName = view.findViewById(R.id.playerName) as TextView
        val playerPosisition = view.findViewById(R.id.playerPosistion) as TextView
        fun bind(player: Player, listener: (Player) -> Unit) {
            playerName.text = player.strPlayer
            playerPosisition.text=player.strPosition
            if(player.strCutout!=null) {
                Picasso.get().load(player.strCutout).into(playerBadge)
            }
                itemView.setOnClickListener {
                listener(player)
            }
        }
    }


}

