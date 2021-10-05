package com.paxcreation.weatherapp.presentation.ui.dialog

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paxcreation.weatherapp.data.manager.SharedPrefsManager
import com.paxcreation.weatherapp.domain.model.Location
import com.paxcreation.weatherapp.domain.model.LocationSearch
import com.paxcreation.weatherapp.domain.model.intent.InitScreenIntent
import com.paxcreation.weatherapp.domain.usecase.AddWeatherLocalUseCase
import com.paxcreation.weatherapp.domain.usecase.GetLocationById
import com.paxcreation.weatherapp.domain.usecase.InitUseCase
import com.paxcreation.weatherapp.presentation.extensions.onError
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BottomViewModel @Inject constructor(
    private val getLocationById: GetLocationById,
    private val initUseCase: InitUseCase,
    private val addWeatherLocalUseCase: AddWeatherLocalUseCase,
    private val sharedPrefsManager: SharedPrefsManager
) : BaseViewModel() {
    val intent = MutableLiveData<InitScreenIntent>()
    val location = MutableLiveData<Location>()
    private val TAG = "BottomViewModel"

    override fun onFirstTimeUiCreate(bundle: Bundle) {
    }


    fun loadLocationById(query: String) {
        viewModelScope.launch {
            getLocationById(query).execute(true,
                com.paxcreation.weatherapp.presentation.extensions.onSuccess {
                    location.value = it
                }, onError {
                    Log.e(TAG, it.toString())
                })
        }
    }

    fun initialize() {
        viewModelScope.launch {
            delay(600L)
            initUseCase().execute(false,
                com.paxcreation.weatherapp.presentation.extensions.onSuccess {
                    intent.postValue(it)
                })
        }
    }

    fun insertWeatherLocal(locationSearch: LocationSearch) {
        try {
            addWeatherLocalUseCase(locationSearch)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    fun checkTemperature() =
        sharedPrefsManager.mSharedPreferences.getInt("temperature", 0) == 1

    fun changeTemperatureCtoF(temp: Float) = (temp * 1.8 + 32).toFloat()

}