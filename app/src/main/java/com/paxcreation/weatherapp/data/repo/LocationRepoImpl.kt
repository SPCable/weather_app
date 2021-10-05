package com.paxcreation.weatherapp.data.repo

import com.paxcreation.weatherapp.data.services.ApiService
import com.paxcreation.weatherapp.domain.model.Location
import com.paxcreation.weatherapp.domain.model.intent.InitScreenIntent
import com.paxcreation.weatherapp.domain.repo.LocationRepo
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocationRepoImpl @Inject constructor(
    private val apiService: ApiService
) : LocationRepo {
    override fun getLocationById(id: String): Flowable<Location> =
        apiService.getLocationByWoid(id)

    override fun test(id: String) {
        apiService.test(id).title
    }

    override fun initStartUpScreen(): Observable<InitScreenIntent> = Observable.create { emitter ->
        emitter.onNext(InitScreenIntent.OpenHome)
        emitter.onComplete()
    }
}