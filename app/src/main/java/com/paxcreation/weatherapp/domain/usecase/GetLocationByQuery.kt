package com.paxcreation.weatherapp.domain.usecase
import com.paxcreation.weatherapp.domain.repo.LocationSearchRepo
import javax.inject.Inject

class GetLocationByQuery @Inject constructor(private val locationSearchRepo : LocationSearchRepo){
    operator fun invoke(query: String) = locationSearchRepo.loadLocationSearch(query)
}