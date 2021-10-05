package com.paxcreation.weatherapp.domain.model.intent

sealed class SplashIntent {
    object Success:SplashIntent()
    object Invalid:SplashIntent()
}