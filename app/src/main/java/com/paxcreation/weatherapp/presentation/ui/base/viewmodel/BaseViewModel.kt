package com.paxcreation.weatherapp.presentation.ui.base.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paxcreation.weatherapp.data.manager.ErrorManager
import com.paxcreation.weatherapp.domain.`interface`.PlainConsumer
import com.paxcreation.weatherapp.domain.define.State
import com.paxcreation.weatherapp.domain.manager.UserManager
import com.paxcreation.weatherapp.presentation.utils.SchedulerProvider
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject


abstract class BaseViewModel : ViewModel() {
    @Inject
    lateinit var userManager: UserManager

    @Inject
    lateinit var errorManager: ErrorManager

    @NonNull
    protected var mCompositeDisposable = CompositeDisposable()

    @NonNull
    val stateLiveData = MutableLiveData<State>()

    private var isFirstTimeUiCreate = true

    @CallSuper
    fun onCreate(bundle: Bundle?) {
        if (isFirstTimeUiCreate) {
            onFirstTimeUiCreate(bundle ?: Bundle())
            isFirstTimeUiCreate = false
        }
    }

    abstract fun onFirstTimeUiCreate(bundle: Bundle)

    open fun onDestroyView() {
        stateLiveData.postValue(State.InitState)
    }

    open fun onResume() {
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposeAllExecutions()
    }

    private fun disposeAllExecutions() {
        mCompositeDisposable.dispose()
        mCompositeDisposable = CompositeDisposable()
        publishState(State.SuccessState())
    }

    @MainThread
    protected fun publishState(state: State) {
        stateLiveData.postValue(state)
        if (mCompositeDisposable.isDisposed) {
            publishState(State.SuccessState())
        }
    }

    open fun onBackPressed(popBackStack: () -> Unit) {}

    protected fun <T> Single<T>.execute(
        showProgress: Boolean = true,
        responseConsumer: PlainConsumer<T>? = null,
        errorConsumer: PlainConsumer<State.ErrorState>? = null
    ) {
        if (showProgress) publishState(State.LoadingState())
        this.compose(if (showProgress) SchedulerProvider.ioToMainSingle() else SchedulerProvider.ioToIoSingle())
            .subscribe({
                if (showProgress) publishState(State.SuccessState())
                responseConsumer?.accept(it)
            }, {
                val errorState = errorManager.getErrorState(it)
                if (showProgress) publishState(errorState)
                errorConsumer?.accept(errorState)
            }).addTo(mCompositeDisposable)
    }

    protected fun <T> Flowable<T>.execute(
        showProgress: Boolean = true,
        responseConsumer: PlainConsumer<T>? = null,
        errorConsumer: PlainConsumer<State.ErrorState>? = null
    ) {
        if (showProgress) publishState(State.LoadingState())
        this.compose(if (showProgress) SchedulerProvider.ioToMainFlowable() else SchedulerProvider.ioToIoFlowable())
            .subscribe({
                if (showProgress) publishState(State.SuccessState())
                responseConsumer?.accept(it)
            }, {
                val errorState = errorManager.getErrorState(it)
                if (showProgress) publishState(errorState)
                errorConsumer?.accept(errorState)

            }).addTo(mCompositeDisposable)
    }

    protected fun <T> Observable<T>.execute(
        showProgress: Boolean = true,
        responseConsumer: PlainConsumer<T>? = null,
        errorConsumer: PlainConsumer<State.ErrorState>? = null
    ) {
        if (showProgress) publishState(State.LoadingState())
        this.compose(
            if (showProgress) SchedulerProvider.ioToMainObservable()
            else {

                SchedulerProvider.ioToIoObservable()
            }
        ).subscribe({
            if (showProgress) publishState(State.SuccessState())
            responseConsumer?.accept(it)
        }, {
            val errorState = errorManager.getErrorState(it)
            if (showProgress) publishState(errorState)
            errorConsumer?.accept(errorState)
        }).addTo(mCompositeDisposable)
    }

}