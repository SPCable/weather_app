package com.paxcreation.weatherapp.domain.usecase

import com.paxcreation.weatherapp.domain.repo.LocationRepo
import javax.inject.Inject

class InitStartUpScreenUseCase @Inject constructor(private val locationRepo: LocationRepo) {
    operator fun invoke() = locationRepo.initStartUpScreen()
}