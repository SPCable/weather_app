package com.paxcreation.weatherapp.domain.manager

import androidx.lifecycle.MutableLiveData
import com.paxcreation.weatherapp.domain.model.User

interface UserManager {
    fun getUserToken(): String?
    fun setUserToken(token: String?)

    fun getUserLiveData(): MutableLiveData<User>
    fun setUser(user: User)
    fun isLogin(): Boolean

    fun getUserId(): Int?
}