package com.paxcreation.weatherapp.presentation.utils.rx

import io.reactivex.rxjava3.annotations.Nullable
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class RxJava3CallAdapter<R>(
    private val responseType: Type,
    private val scheduler: @Nullable Scheduler?,
    private val isAsync: Boolean,
    private val isResult: Boolean,
    private val isBody: Boolean,
    private val isFlowable: Boolean,
    private val isSingle: Boolean,
    private val isMaybe: Boolean,
    private val isCompletable: Boolean
) : CallAdapter<R, Any> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): Any {
        val responseObservable =
            if (isAsync) CallEnqueueObservable (call) else CallExecuteObservable(call)
        var observable: Observable<*>
        observable = if (isResult) {
            ResultObservable(responseObservable)
        } else if (isBody) {
            BodyObservable(responseObservable)
        } else {
            responseObservable
        }
        if (scheduler != null) {
            observable = observable.subscribeOn(scheduler)
        }
        if (isFlowable) {
            return observable.toFlowable(BackpressureStrategy.LATEST)
        }
        if (isSingle) {
            return observable.singleOrError()
        }
        if (isMaybe) {
            return observable.singleElement()
        }
        return if (isCompletable) {
            observable.ignoreElements()
        } else RxJavaPlugins.onAssembly(observable)
    }

}