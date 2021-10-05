package com.paxcreation.weatherapp.domain.model

data class WeatherSearch(
    var name: String,
    var id: Int,
    var status: String,
    var temp: Float,
)