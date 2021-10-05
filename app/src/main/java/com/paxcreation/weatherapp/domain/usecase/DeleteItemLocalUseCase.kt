package com.paxcreation.weatherapp.domain.usecase

import com.paxcreation.weatherapp.domain.repo.LocationSearchRepo
import javax.inject.Inject

class DeleteItemLocalUseCase @Inject constructor(private val locationSearchRepo: LocationSearchRepo) {
    operator fun invoke(id: Int) =
        locationSearchRepo.deleteWeatherLocal(id)
}