package com.paxcreation.weatherapp.domain.define

import android.webkit.ConsoleMessage

sealed class State(var message: String = "") {
    object InitState: State("")
    data class SuccessState(var successMessage: String = "") : State(successMessage)
    data class ErrorState(var errorMessage: String = "", var status: Int? = -1, var reason: String? = "") : State(errorMessage)
    data class LoadingState(var loadingMessage: String = "") : State(loadingMessage)
}