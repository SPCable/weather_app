package com.paxcreation.weatherapp.presentation.ui.init

import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.presentation.ui.base.activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitActivity : BaseActivity() {
    override fun getLayoutResource() = R.layout.activity_splash

    override fun getScreenName(): String?  = null

    override fun onBackPressed() {
        if (getBackStackCount() == 1){
            finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun getBackStackCount(): Int {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
        return navHostFragment?.childFragmentManager?.backStackEntryCount?:0
    }
}