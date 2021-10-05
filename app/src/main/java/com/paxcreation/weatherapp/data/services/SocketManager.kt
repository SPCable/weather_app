package com.paxcreation.weatherapp.data.services

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.paxcreation.weatherapp.domain.config.AppConfig
import com.paxcreation.weatherapp.domain.manager.UserManager
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber

class SocketManager constructor(
    @ApplicationContext val context: Context,
    val gson: Gson,
    val userManager: UserManager
) {
    private var isConnected = false
    fun connect() {}
}