package com.paxcreation.weatherapp.presentation.ui.main

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.paxcreation.weatherapp.data.manager.SharedPrefsManager
import com.paxcreation.weatherapp.domain.model.LocationSearch
import com.paxcreation.weatherapp.domain.repo.LocationRepo
import com.paxcreation.weatherapp.domain.usecase.GetWeathersLocalUseCase
import com.paxcreation.weatherapp.presentation.extensions.onSuccess
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModel
import com.paxcreation.weatherapp.presentation.utils.NavigatorHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepo: LocationRepo,
    private val navigatorHelper: NavigatorHelper,
    private val sharedPrefsManager: SharedPrefsManager,
    private val getWeathersLocalUseCase: GetWeathersLocalUseCase
) : BaseViewModel() {

    private val TAG = "MainViewModel"
    val listWeatherLiveData = MutableLiveData<List<LocationSearch>>()
    val listWeather = ArrayList<LocationSearch>()
    override fun onFirstTimeUiCreate(bundle: Bundle) {
        loadWeatherLocal()
    }


    fun loadWeatherLocal() {
        getWeathersLocalUseCase().execute(
            true,
            onSuccess {
                it.forEach { locationSearch ->
                    listWeather.add(locationSearch)
                }
                listWeatherLiveData.postValue(listWeather)
            }
        )
    }

    fun openSettingActivity(context: Context) {
        navigatorHelper.actionOpenSettingActivity(context)
    }
}