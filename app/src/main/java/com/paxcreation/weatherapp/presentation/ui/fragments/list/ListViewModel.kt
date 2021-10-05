package com.paxcreation.weatherapp.presentation.ui.fragments.list

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.paxcreation.weatherapp.domain.model.LocationSearch
import com.paxcreation.weatherapp.domain.model.WeatherSearch
import com.paxcreation.weatherapp.domain.usecase.DeleteItemLocalUseCase
import com.paxcreation.weatherapp.domain.usecase.GetLocationById
import com.paxcreation.weatherapp.domain.usecase.GetWeathersLocalUseCase
import com.paxcreation.weatherapp.presentation.extensions.onSuccess
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getLocationById: GetLocationById,
    private val getWeathersLocalUseCase: GetWeathersLocalUseCase,
    private val deleteItemLocalUseCase: DeleteItemLocalUseCase
) : BaseViewModel() {

    val locationSearch = MutableLiveData<List<LocationSearch>>()
    private val listWeatherSearch = ArrayList<WeatherSearch>()
    var locationSearchLiveData = MutableLiveData<List<WeatherSearch>>()
    private val TAG = "ListViewModel"


    override fun onFirstTimeUiCreate(bundle: Bundle) {
        loadWeatherLocal()
    }

    private fun loadWeatherLocal() {
        getWeathersLocalUseCase().execute(true, onSuccess { locationSearch ->
            locationSearch.forEach { it ->
                getLocationById(it.woeid.toString()).execute(
                    true,
                    onSuccess { location ->
                        listWeatherSearch.add(
                            WeatherSearch(
                                it.title,
                                it.woeid,
                                location.consolidated_weather[0].weather_state_abbr,
                                location.consolidated_weather[0].the_temp
                            )
                        )
                        if (listWeatherSearch.size == locationSearch.size) {
                            listWeatherSearch.sortBy { it.name }
                            locationSearchLiveData.value = listWeatherSearch
                        }
                    }
                )
            }
        })
    }


    fun deleteItem(id: Int) {
        deleteItemLocalUseCase(id)
    }
}