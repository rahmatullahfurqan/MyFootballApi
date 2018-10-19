package com.example.rahma.myfootballapi.model

import com.google.gson.annotations.SerializedName

data class Team(
        @SerializedName("idTeam")
        val intTeamId: String? = "",

        @SerializedName("strTeam")
        val strTeamName: String? = "",

        @SerializedName("strTeamBadge")
        val strTeamBadge: String? = "",

        @SerializedName("intFormedYear")
        var intTeamFormedYear: String? = "",

        @SerializedName("strStadium")
        var strTeamStadium: String? = "",

        @SerializedName("strDescriptionEN")
        var strTeamDescription: String?
)