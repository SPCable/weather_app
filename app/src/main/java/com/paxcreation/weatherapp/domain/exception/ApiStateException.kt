package com.paxcreation.weatherapp.domain.exception

class ApiStateException(
    var code: Int?,
    var errorMessage: String?,
) : Throwable()