package com.paxcreation.weatherapp.presentation.ui.base.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.paxcreation.weatherapp.domain.define.State
import com.paxcreation.weatherapp.presentation.extensions.showToast
import com.paxcreation.weatherapp.presentation.ui.base.viewmodel.BaseViewModel
import com.paxcreation.weatherapp.presentation.utils.NavigatorHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModelFragment<B : ViewDataBinding, VM : BaseViewModel> : BaseFragment<B>() {

    lateinit var viewModel: VM

    @Inject
    lateinit var navigatorHelper: NavigatorHelper

    private var isShowingToast: Boolean = false

    protected abstract fun createViewModelClass(): Class<VM>

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(createViewModelClass())
        viewModel.onCreate(getFragmentArguments())
        viewModel.stateLiveData.observe(viewLifecycleOwner, {
            handleState(it)
        })
        initObserver()
        initialize(view, requireActivity())
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed(), {
            viewModel.onBackPressed {
                parentFragment?.findNavController()?.navigateUp()
            }
        })
    }

    protected abstract fun initialize(view: View, ctx: Context?)

    open fun initObserver() {
    }

    open fun handleState(state: State?) {
        setLoading(state != null && state is State.LoadingState)
        handleMessageState(state)
    }

    private fun handleMessageState(@Nullable state: State?) {
        if ((state is State.SuccessState || state is State.ErrorState) && state?.message?.isNotEmpty()) {
            showMessage(state.message)
        }
    }

    private fun showMessage(message: String?) {
        if (!isShowingToast) {
            lifecycleScope.launch {
                isShowingToast = true
                context?.showToast(message)
                delay(2000L)
                isShowingToast = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroyView()
        setLoading(false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}