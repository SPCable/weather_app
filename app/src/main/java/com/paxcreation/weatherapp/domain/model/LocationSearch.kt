package com.paxcreation.weatherapp.domain.model


data class LocationSearch(
    var title: String,
    var location_type: String,
    var latt_long: String,
    var woeid: Int,
)