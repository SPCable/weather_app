package com.paxcreation.weatherapp.presentation.ui.base.activity

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.paxcreation.weatherapp.domain.define.State
import com.paxcreation.weatherapp.presentation.extensions.showToast
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

abstract class BaseViewModelActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    @VisibleForTesting
    lateinit var binding: B
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResource())
        viewModel = ViewModelProvider(this).get(createViewModelClass())
        viewModel.onCreate(intent.extras)
        viewModel.stateLiveData.observe(this, { handleState(it) })
        initObserver()
    }

    protected abstract fun initObserver()

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun shouldUseDataBinding() = true

    override fun onBackPressed() {
        val countFragment = supportFragmentManager.backStackEntryCount
        if (countFragment == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun getScreenName(): String? = null

    open fun handleState(it: State?) {
        handleMessageState(it)
        setLoading(it != null && it is State.LoadingState)
    }

    private fun handleMessageState(@Nullable state: State?) {
        if ((state is State.SuccessState || state is State.ErrorState) && state.message.isNotEmpty()) {
            showToast(state.message)
        }
    }

    protected abstract fun createViewModelClass(): Class<VM>
}