package com.paxcreation.weatherapp.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.databinding.ActivitySplashBinding
import com.paxcreation.weatherapp.domain.model.intent.InitScreenIntent
import com.paxcreation.weatherapp.presentation.extensions.navigateActivity
import com.paxcreation.weatherapp.presentation.ui.base.activity.BaseViewModelActivity
import com.paxcreation.weatherapp.presentation.ui.login.LoginActivity
import com.paxcreation.weatherapp.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseViewModelActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun createViewModelClass() = SplashViewModel::class.java
    override fun getScreenName(): String? = null
    override fun getLayoutResource() = R.layout.activity_splash


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setLoading(true)
    }

    override fun initObserver() {
        viewModel.intent.observe(this, {
            when (it) {
                InitScreenIntent.OpenHome -> {
                    navigateActivity<LoginActivity> {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    finish()
                }
                else -> {
                    navigateActivity<MainActivity> {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    finish()
                }
            }
        })
    }
}