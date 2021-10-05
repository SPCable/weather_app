package com.paxcreation.weatherapp.presentation.ui.login

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paxcreation.weatherapp.data.manager.SharedPrefsManager
import com.paxcreation.weatherapp.domain.config.KeyConfig
import com.paxcreation.weatherapp.domain.model.intent.InitScreenIntent
import com.paxcreation.weatherapp.domain.usecase.InitUseCase
import com.paxcreation.weatherapp.domain.usecase.LoginUseCase
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModel
import com.paxcreation.weatherapp.presentation.utils.NavigatorHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val navigatorHelper: NavigatorHelper,
    private val sharedPrefsManager: SharedPrefsManager
) : BaseViewModel() {
    var intent = MutableLiveData<InitScreenIntent>()

    override fun onFirstTimeUiCreate(bundle: Bundle) {
    }

    fun doLoginWithFacebook() {

    }

    fun doLoginWithGoogle() {

    }

    fun doSkip(context: Context) {
        sharedPrefsManager.put(KeyConfig.IS_SKIP_MEMBER, true)
        navigatorHelper.actionOpenMainActivity(context)
    }


}