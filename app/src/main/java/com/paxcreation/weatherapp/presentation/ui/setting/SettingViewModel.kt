package com.paxcreation.weatherapp.presentation.ui.setting

import android.content.Context
import android.os.Bundle
import com.paxcreation.weatherapp.data.manager.SharedPrefsManager
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModel
import com.paxcreation.weatherapp.presentation.utils.NavigatorHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val navigatorHelper: NavigatorHelper,
    private val sharedPrefsManager: SharedPrefsManager
) : BaseViewModel() {
    override fun onFirstTimeUiCreate(bundle: Bundle) {
    }


    fun changeTemperatureUnit(boolean: Boolean) {
        if (boolean) {
            sharedPrefsManager.put("temperature", 1)
        } else {
            sharedPrefsManager.put("temperature", 0)
        }
    }

    fun backToMainActivity(context: Context)
    {
        navigatorHelper.actionOpenMainActivity(context)
    }
}