package com.paxcreation.weatherapp.domain.model.intent

sealed class InitScreenIntent {
    object OpenHome: InitScreenIntent()
    object OpenRegister: InitScreenIntent()
    object OpenSetting : InitScreenIntent()
}