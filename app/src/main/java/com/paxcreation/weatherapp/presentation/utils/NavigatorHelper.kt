package com.paxcreation.weatherapp.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.paxcreation.weatherapp.domain.manager.UserManager
import com.paxcreation.weatherapp.presentation.ui.main.MainActivity
import com.paxcreation.weatherapp.presentation.ui.setting.SettingActivity
import dagger.hilt.android.internal.managers.FragmentComponentManager

class NavigatorHelper(private val userManager: UserManager) {
    private fun getActivity(context: Context): Activity {
        return FragmentComponentManager.findActivity(context) as Activity
    }

    fun actionOpenMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)

    }


    fun actionOpenSettingActivity(context: Context) {
        val intent = Intent(
            context,
            SettingActivity::class.java
        ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
    }


    fun onBackPressed(context: Context) {
        getActivity(context).onBackPressed()
    }

}