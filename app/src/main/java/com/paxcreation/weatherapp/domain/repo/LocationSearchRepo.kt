package com.paxcreation.weatherapp.domain.repo

import com.paxcreation.weatherapp.domain.model.LocationSearch
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

interface LocationSearchRepo {
    fun loadLocationSearch(query: String): Observable<List<LocationSearch>>
    fun getAllWeathersLocal(): Observable<List<LocationSearch>>
    fun addWeatherLocal(locationSearch: LocationSearch)
    fun deleteWeatherLocal(id:Int)
}