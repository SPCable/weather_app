package com.paxcreation.weatherapp.data.manager

import androidx.lifecycle.MutableLiveData
import com.paxcreation.weatherapp.domain.manager.UserManager
import com.paxcreation.weatherapp.domain.model.User
import javax.inject.Inject

class UserManagerImpl @Inject constructor() : UserManager {

    private var mUserToken: String? = null


    private var mUser = MutableLiveData<User>()

    override fun getUserToken(): String? = mUserToken

    override fun setUserToken(token: String?) {
        this.mUserToken = token
    }

    override fun getUserLiveData(): MutableLiveData<User> {
        return mUser
    }

    override fun setUser(user: User) {
        mUser.postValue(user)
    }

    override fun isLogin(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUserId(): Int? {
        TODO("Not yet implemented")
    }



}