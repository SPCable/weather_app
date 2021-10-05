package com.paxcreation.weatherapp.domain.repo

import io.reactivex.rxjava3.core.Single

interface UserRepo {
    fun login(account: String, password: String): Single<Unit>
    fun getUserToken(): Single<String>
    fun logOut(): Single<Unit>
    fun saveUserToken(token: String?): Single<Unit>
}