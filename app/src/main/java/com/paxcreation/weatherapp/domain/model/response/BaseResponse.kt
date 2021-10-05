package com.paxcreation.weatherapp.domain.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("_data")
    var data: T?
)
