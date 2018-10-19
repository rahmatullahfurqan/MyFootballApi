package com.example.rahma.myfootballapi.api

import java.net.URL

class Api {

    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}