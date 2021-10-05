package com.paxcreation.weatherapp.presentation.utils

import com.google.gson.Gson

object AppConverters {

    private lateinit var gson: Gson

    fun initialize(gson: Gson) {
        this.gson = gson
    }
}