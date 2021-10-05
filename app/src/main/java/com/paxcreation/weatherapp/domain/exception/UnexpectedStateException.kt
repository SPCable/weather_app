package com.paxcreation.weatherapp.domain.exception

class UnexpectedStateException (
    val code: Int = -1,
    val reason: String = "",
    val errorMessage : String = ""
) : Throwable()