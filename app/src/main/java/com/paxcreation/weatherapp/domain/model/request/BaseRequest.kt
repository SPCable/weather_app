package com.paxcreation.weatherapp.domain.model.request

import com.paxcreation.weatherapp.domain.config.AppConfig
import com.google.gson.annotations.SerializedName

open class BaseRequest {
    @SerializedName("app_code")
    var appCode : String = AppConfig.APP_CODE
}