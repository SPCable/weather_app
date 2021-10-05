package com.paxcreation.weatherapp.domain.usecase

import com.paxcreation.weatherapp.domain.model.intent.InitScreenIntent
import com.paxcreation.weatherapp.domain.repo.LocationRepo
import javax.inject.Inject

import io.reactivex.rxjava3.core.Observable

class InitUseCase @Inject constructor(
    private val locationRepo: LocationRepo

) {
    operator fun invoke(): Observable<InitScreenIntent> =
        locationRepo.initStartUpScreen()
}