package com.paxcreation.weatherapp.presentation.utils.rx

import io.reactivex.rxjava3.annotations.Nullable
import io.reactivex.rxjava3.core.*
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RxJava3CallAdapterFactory private constructor(
    private val scheduler: @Nullable Scheduler?,
    private val isAsync: Boolean
) : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): @Nullable CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (rawType == Completable::class.java) {
            return RxJava3CallAdapter<Any>(
                Void::class.java, scheduler, isAsync, false, true, false, false,
                false, true
            )
        }
        val isFlowable = rawType == Flowable::class.java
        val isSingle = rawType == Single::class.java
        val isMaybe = rawType == Maybe::class.java
        if (rawType != Observable::class.java && !isFlowable && !isSingle && !isMaybe) {
            return null
        }
        var isResult = false
        var isBody = false
        val responseType: Type
        if (returnType !is ParameterizedType) {
            val name =
                if (isFlowable) "Flowable" else if (isSingle) "Single" else if (isMaybe) "Maybe" else "Observable"
            throw IllegalStateException(
                name + " return type must be parameterized"
                        + " as " + name + "<Foo> or " + name + "<? extends Foo>"
            )
        }
        val observableType =
            getParameterUpperBound(
                0,
                returnType
            )
        val rawObservableType =
            getRawType(observableType)
        if (rawObservableType == Response::class.java) {
            check(observableType is ParameterizedType) {
                ("Response must be parameterized"
                        + " as Response<Foo> or Response<? extends Foo>")
            }
            responseType = getParameterUpperBound(
                0,
                observableType
            )
        } else if (rawObservableType == Result::class.java) {
            check(observableType is ParameterizedType) {
                ("Result must be parameterized"
                        + " as Result<Foo> or Result<? extends Foo>")
            }
            responseType = getParameterUpperBound(
                0,
                observableType
            )
            isResult = true
        } else {
            responseType = observableType
            isBody = true
        }
        return RxJava3CallAdapter<Any>(
            responseType, scheduler, isAsync, isResult, isBody, isFlowable,
            isSingle, isMaybe, false
        )
    }

    companion object {
        fun create(): RxJava3CallAdapterFactory {
            return RxJava3CallAdapterFactory(null, false)
        }


        fun createAsync(): RxJava3CallAdapterFactory {
            return RxJava3CallAdapterFactory(null, true)
        }


        fun createWithScheduler(scheduler: Scheduler?): RxJava3CallAdapterFactory {
            if (scheduler == null) throw NullPointerException("scheduler == null")
            return RxJava3CallAdapterFactory(scheduler, false)
        }
    }

}