package com.paxcreation.weatherapp.data.repo


import com.paxcreation.weatherapp.data.manager.DatabaseManager
import com.paxcreation.weatherapp.data.mapper.LocationSearchMapper
import com.paxcreation.weatherapp.data.services.ApiService
import com.paxcreation.weatherapp.domain.model.LocationSearch
import com.paxcreation.weatherapp.domain.repo.LocationSearchRepo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LocationSearchRepoImpl @Inject constructor(
    private val apiServices: ApiService,
    private val database: DatabaseManager,
    private val locationSearchMapper: LocationSearchMapper
) : LocationSearchRepo {
    override fun loadLocationSearch(query: String): Observable<List<LocationSearch>> =
        apiServices.getLocation(query).subscribeOn(Schedulers.io())

    override fun getAllWeathersLocal(): Observable<List<LocationSearch>> =
        database.locationDao().getAllData().map { list ->
            list.map {
                locationSearchMapper.mapFromEntity(it)
            }
        }

    override fun addWeatherLocal(locationSearch: LocationSearch) {
        database.locationDao().insert(locationSearchMapper.mapToEntity(locationSearch))
    }

    override fun deleteWeatherLocal(id: Int) {
        database.locationDao().deleteById(id)
    }


}