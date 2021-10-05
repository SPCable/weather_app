package com.paxcreation.weatherapp.domain.usecase

import com.paxcreation.weatherapp.domain.model.LocationSearch
import com.paxcreation.weatherapp.domain.repo.LocationSearchRepo
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetWeathersLocalUseCase @Inject constructor(private val locationSearchRepo: LocationSearchRepo) {
    operator fun invoke(): Observable<List<LocationSearch>> = locationSearchRepo.getAllWeathersLocal()
}