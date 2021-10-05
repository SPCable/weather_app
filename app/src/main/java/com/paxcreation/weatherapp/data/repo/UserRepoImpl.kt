package com.paxcreation.weatherapp.data.repo

import com.paxcreation.weatherapp.data.manager.SharedPrefsManager
import com.paxcreation.weatherapp.domain.manager.UserManager
import com.paxcreation.weatherapp.domain.repo.UserRepo
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val userManager: UserManager
):UserRepo {
    override fun login(account: String, password: String): Single<Unit> {
        TODO("Not yet implemented")
    }

    override fun getUserToken(): Single<String> {
        TODO("Not yet implemented")
    }

    override fun logOut(): Single<Unit> {
        TODO("Not yet implemented")
    }

    override fun saveUserToken(token: String?): Single<Unit> {
        TODO("Not yet implemented")
    }

}