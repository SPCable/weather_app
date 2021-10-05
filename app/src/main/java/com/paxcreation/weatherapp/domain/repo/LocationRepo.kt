package com.paxcreation.weatherapp.domain.repo

import com.paxcreation.weatherapp.domain.model.Location
import com.paxcreation.weatherapp.domain.model.intent.InitScreenIntent
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface LocationRepo {
    fun getLocationById(id: String): Flowable<Location>
    fun test(id: String)
    fun initStartUpScreen(): Observable<InitScreenIntent>
}