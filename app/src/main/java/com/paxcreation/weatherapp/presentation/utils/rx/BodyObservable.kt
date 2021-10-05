package com.paxcreation.weatherapp.presentation.utils.rx

import com.paxcreation.weatherapp.domain.model.response.BaseResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.exceptions.CompositeException
import io.reactivex.rxjava3.exceptions.Exceptions
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import retrofit2.HttpException
import retrofit2.Response

internal class BodyObservable<T>(private val upstream: Observable<Response<T>>) :
    Observable<T>() {
    override fun subscribeActual(observer: Observer<in T>) {
        upstream.subscribe(BodyObserver(observer))
    }

    private class BodyObserver<R> internal constructor(observer: Observer<in R>) :
        Observer<Response<R>> {
        private val observer: Observer<in R?> = observer as Observer<in R?>
        private var terminated = false
        override fun onSubscribe(disposable: Disposable) {
            observer.onSubscribe(disposable)
        }

        override fun onNext(response: Response<R>) {
            if (response.isSuccessful) {
                if (response.body() is BaseResponse<*>) {
                    val baseResponse = response.body() as BaseResponse<*>?
//                    if (baseResponse?.isSuccess() == true) {
//                        observer.onNext(response.body())
//                    } else {
//                        observer.onError(ApiStateException(baseResponse?.code, ""))
//                    }
                    observer.onNext(response.body())
                } else {
                    observer.onNext(response.body())
                }
            } else {
                terminated = true
                val t: Throwable = HttpException(response)
                try {
                    observer.onError(t)
                } catch (inner: Throwable) {
                    Exceptions.throwIfFatal(inner)
                    RxJavaPlugins.onError(
                        CompositeException(
                            t,
                            inner
                        )
                    )
                }
            }
        }

        override fun onComplete() {
            if (!terminated) {
                observer.onComplete()
            }
        }

        override fun onError(throwable: Throwable) {
            if (!terminated) {
                observer.onError(throwable)
            } else {
                val broken: Throwable = AssertionError(
                )
                broken.initCause(throwable)
                RxJavaPlugins.onError(broken)
            }
        }

    }

}