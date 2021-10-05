package com.paxcreation.weatherapp.domain.model


data class Location(
    var woeid: Int,
    var title: String,
    var location_type: String,
    var latt_long: String,
    var time: String,
    var sun_rise: String,
    var sun_set: String,
    var timezone_name: String,
    var parent: Parent,
    var consolidated_weather: List<ConsolidatedWeather>,
    var sources: List<Sources>
) {
    data class Parent(
        var title: String,
        var location_type: String,
        var latt_long: String,
        var woeid: Int,
    )

    data class ConsolidatedWeather(
        var id: Int,
        var applicable_date: String,
        var weather_state_name: String,
        var weather_state_abbr: String,
        var wind_speed: Float,
        var wind_direction: Float,
        var wind_direction_compass: String,
        var min_temp: Float,
        var max_temp: Float,
        var the_temp: Float,
        var air_pressure: Float,
        var humidity: Int,
        var visibility: Float,
        var predictability: Int,
        var created: String
    )

    data class Sources(
        var title: String,
        var slug: String,
        var url: String,
        var crawl_rate: Int,
    )
}

