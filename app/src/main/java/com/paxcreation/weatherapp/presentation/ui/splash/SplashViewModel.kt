package com.paxcreation.weatherapp.presentation.ui.splash

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paxcreation.weatherapp.domain.model.intent.InitScreenIntent
import com.paxcreation.weatherapp.domain.usecase.InitUseCase
import com.paxcreation.weatherapp.presentation.extensions.onSuccess
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val initUseCase: InitUseCase
) : BaseViewModel() {
    var intent = MutableLiveData<InitScreenIntent>()

    fun initialize() {
        viewModelScope.launch {
            delay(600L)
            initUseCase().execute(false, onSuccess {
                intent.postValue(it)
            })
        }
    }

    override fun onFirstTimeUiCreate(bundle: Bundle) {
        initialize()
    }
}