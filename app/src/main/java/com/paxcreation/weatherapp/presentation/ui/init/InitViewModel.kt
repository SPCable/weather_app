package com.paxcreation.weatherapp.presentation.ui.init

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.paxcreation.weatherapp.domain.model.intent.InitScreenIntent
import com.paxcreation.weatherapp.domain.usecase.InitStartUpScreenUseCase
import com.paxcreation.weatherapp.presentation.extensions.onSuccess
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    private val initStartUpScreenUseCase: InitStartUpScreenUseCase
) : BaseViewModel() {

    var initIntent = MutableLiveData<InitScreenIntent>()

    override fun onFirstTimeUiCreate(bundle: Bundle) {
        initStartUpScreenUseCase().execute(false, onSuccess {
            initIntent.postValue(it)
        })
    }
}