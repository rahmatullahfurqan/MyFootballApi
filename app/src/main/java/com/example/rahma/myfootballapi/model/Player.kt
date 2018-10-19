package com.example.rahma.myfootballapi.model

import com.google.gson.annotations.SerializedName

data class Player(
        @SerializedName("idPlayer")
        val idPlayer: String? = "",

        @SerializedName("strPlayer")
        val strPlayer: String? = "",

        @SerializedName("strPosition")
        val strPosition: String? = "",

        @SerializedName("strCutout")
        var strCutout: String? = "",

        @SerializedName("strWeight")
        val strWeight: String? = "",

        @SerializedName("strHeight")
        var strHeight: String? = "",

        @SerializedName("strFanart1")
        val strFanart1: String? = "",

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String? = ""


)