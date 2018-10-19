package com.example.rahma.myfootballapi

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}
fun getMatch()="Match"
fun getNextMatch()="Next Match"
fun getFavoriteMatch()="Match"
fun getFavorite()="Favorite"
fun getPastLeague()="eventspastleague.php"
fun getNextLeague()="eventsnextleague.php"
fun getIdEvent()="idEvent"
fun getOldSdf()="yyyy-MM-dd"
fun getNewSdf()="EEEE,d MMM yyyy"
fun getVs()=" vs "
fun getTeam()="TEAM"
fun getIdTeam()="idTeam"
fun getOverview()="Overview"
fun getFavoriteTeam()="Team"
fun getPlayer()="Player"
fun getIdPlayer()="idPlayer"
fun getIdLeague(leagueName : String):String{
    var idLeague = "4328"
    when (leagueName) {
        "English Premier League" -> idLeague = "4328"
        "English League Championship" -> idLeague = "4329"
        "German Bundesliga" -> idLeague = "4331"
        "Italian Serie A" -> idLeague = "4332"
        "French Ligue 1" -> idLeague = "4334"
        "Spanish La Liga" -> idLeague = "4335"
        "Netherlands Eredivisie" -> idLeague = "4337"
    }
    return idLeague
}