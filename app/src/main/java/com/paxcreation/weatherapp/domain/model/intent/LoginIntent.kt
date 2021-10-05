package com.paxcreation.weatherapp.domain.model.intent

sealed class LoginIntent {
    data class Success(val message: String): LoginIntent()
    data class Error(val message: String): LoginIntent()
}