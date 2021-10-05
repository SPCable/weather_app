package com.paxcreation.weatherapp.presentation.ui.init

import android.content.Context
import android.content.Intent
import android.view.View
import com.paxcreation.weatherapp.R
import com.paxcreation.weatherapp.databinding.FragmentInitBinding
import com.paxcreation.weatherapp.domain.model.intent.InitScreenIntent
import com.paxcreation.weatherapp.presentation.extensions.navigateActivity
import com.paxcreation.weatherapp.presentation.ui.base.fragments.BaseViewModelFragment
import com.paxcreation.weatherapp.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitFragment : BaseViewModelFragment<FragmentInitBinding, InitViewModel>() {

    override fun createViewModelClass() = InitViewModel::class.java

    override fun initialize(view: View, ctx: Context?) {
        viewModel.initIntent.observe(viewLifecycleOwner, {
            when (it) {
                InitScreenIntent.OpenHome -> {
                    activity?.navigateActivity<MainActivity> {
                        if (requireActivity().intent.data != null && activity?.intent?.dataString?.contains(
                                requireActivity().getString(
                                    R.string.app_name
                                )
                            ) == true
                        ) {
                            val intentData = requireActivity().intent?.data
                            if (intentData != null) {
                                data = intentData
                            }
                        }
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    activity?.finish()
                }
            }
        })
    }

    override fun isGradientStatusBar() = true

    override fun getLayoutResource() = R.layout.fragment_home
}