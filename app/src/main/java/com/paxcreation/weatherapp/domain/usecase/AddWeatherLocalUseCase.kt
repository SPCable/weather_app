package com.paxcreation.weatherapp.domain.usecase

import com.paxcreation.weatherapp.domain.model.LocationSearch
import com.paxcreation.weatherapp.domain.repo.LocationSearchRepo
import javax.inject.Inject

class AddWeatherLocalUseCase @Inject constructor(private val locationSearchRepo: LocationSearchRepo) {
    operator fun invoke(locationSearch: LocationSearch) =
        locationSearchRepo.addWeatherLocal(locationSearch)
}