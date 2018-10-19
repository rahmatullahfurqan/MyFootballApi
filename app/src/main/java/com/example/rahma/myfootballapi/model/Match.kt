package com.example.rahma.myfootballapi.model

import com.google.gson.annotations.SerializedName


data class Match(
        @SerializedName("idEvent")
        val idEvent: String? = "",
        @SerializedName("strEvent")
        val strEvent: String? = "",
        @SerializedName("strHomeTeam")
        val strHomeTeam: String? = "",
        @SerializedName("intHomeScore")
        val intHomeScore: String? = "",
        @SerializedName("strAwayTeam")
        val strAwayTeam: String? = "",
        @SerializedName("intAwayScore")
        val intAwayScore: String? = "",
        @SerializedName("dateEvent")
        val dateEvent: String? = "",
        @SerializedName("strTime")
        val strTime: String? = "")