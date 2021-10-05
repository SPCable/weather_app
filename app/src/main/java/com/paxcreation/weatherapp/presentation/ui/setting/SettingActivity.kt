package com.paxcreation.weatherapp.presentation.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.data.manager.SharedPrefsManager
import com.paxcreation.weatherapp.databinding.ActivitySettingBinding
import com.paxcreation.weatherapp.presentation.ui.base.activity.BaseViewModelActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BaseViewModelActivity<ActivitySettingBinding, SettingViewModel>() {
    private val TAG = "SettingActivity"
    private var flag = false

    @Inject
    lateinit var sharedPreferences: SharedPrefsManager

    override fun getLayoutResource() = R.layout.activity_setting

    override fun initObserver() {}

    override fun createViewModelClass() = SettingViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)

        binding.switchTemp.isChecked =
            sharedPreferences.mSharedPreferences.getInt("temperature", 0) == 1
        flag = binding.switchTemp.isChecked

        binding.switchTemp.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.changeTemperatureUnit(isChecked)
            } else {
                viewModel.changeTemperatureUnit(isChecked)
            }
        }

        binding.setOnClickBack {
            if (binding.switchTemp.isChecked == flag) {
                onBackPressed()
            } else {
                viewModel.backToMainActivity(this)
                flag = binding.switchTemp.isChecked
            }
        }
    }


}