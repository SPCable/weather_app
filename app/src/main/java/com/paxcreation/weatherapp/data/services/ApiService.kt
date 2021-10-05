package com.paxcreation.weatherapp.data.services

import com.paxcreation.weatherapp.domain.model.Location
import com.paxcreation.weatherapp.domain.model.LocationSearch
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("location/search/?query=")
    fun getLocation(@Query("query") type: String): Observable<List<LocationSearch>>

    @GET("location/{id}")
    fun getLocationByWoid(@Path("id") id: String): Flowable<Location>

    @GET("location/{id}")
    fun test(@Path("id") id: String): Location

}