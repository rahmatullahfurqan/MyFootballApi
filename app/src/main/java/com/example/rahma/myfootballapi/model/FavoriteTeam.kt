package com.example.rahma.myfootballapi.model

data class FavoriteTeam(
        val id: Long?,
        val idTeam: String?="",
        val strTeam: String?="",
        val strTeamBadge: String?=""
) {

    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID : String = "id_"
        const val ID_TEAM: String = "ID_TEAM"
        const val STR_TEAM : String = "str_team"
        const val STR_TEAM_BADGE :String = "str_team_badge"
    }
}