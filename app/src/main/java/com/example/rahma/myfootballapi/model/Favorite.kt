package com.example.rahma.myfootballapi.model

data class Favorite(
        val id: Long?,
        val idEvent: String?="",
        val strHomeTeam: String?="",
        val strAwayTeam: String?="",
        val strHomeScore: String?="",
        val strAwayScore: String? = "",
        val dateEvent: String?="",
        val strTime : String?=""
) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID : String = "id_"
        const val ID_EVENT: String = "id_event"
        const val strHomeTeam: String = "str_home_team"
        const val strAwayTeam: String = "str_away_team"
        const val str_Home_Score: String = "str_home_score"
        const val str_Away_Score: String = "str_away_score"
        const val dateEvent : String = "date_event"
        const val strTime : String = "str_time"
    }
}